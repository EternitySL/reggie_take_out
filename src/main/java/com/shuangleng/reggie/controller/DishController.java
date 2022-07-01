package com.shuangleng.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuangleng.reggie.common.R;
import com.shuangleng.reggie.dto.DishDto;
import com.shuangleng.reggie.entity.Category;
import com.shuangleng.reggie.entity.Dish;
import com.shuangleng.reggie.service.imp.CategoryServiceImp;
import com.shuangleng.reggie.service.imp.DishServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author ：shuangleng
 * @date ：Created in 2022/6/3 20:52
 * @description：菜品
 */
@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishServiceImp dishServiceImp;
    @Autowired
    private CategoryServiceImp categoryServiceImp;
    //测试添加到缓存
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/page")
    public R<Page<DishDto>> page(int page, int pageSize, String name) {
        //这里需要将菜品的categoryName封装进去
        Page<Dish> dishPage = new Page<>(page, pageSize);
        Page<DishDto> p = new Page<>();
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.like(name != null, Dish::getName, name);
        lqw.orderByAsc(Dish::getPrice);
        //这里进行查询，将数据查询到page中，然后包装到dtopage中
        //因为page查询对不同泛型有要求这里需要dish泛型
        //而这里的dto没有对应的mapper与service
        dishServiceImp.page(dishPage, lqw);
        //对象拷贝
        //改变records中的list泛型
        BeanUtils.copyProperties(dishPage, p, "records");
        //将records中的数据流提取出来，然后进行属性拷贝，将查询的category添加上去，包装成dto的list
        List<Dish> records = dishPage.getRecords();
        List<DishDto> list1 = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();//分类id
            Category category = categoryServiceImp.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());
        p.setRecords(list1);
        return R.success(p);
    }

    /**
     * @Description: 添加菜品
     * @Param: [dishDto]
     * @return: com.shuangleng.reggie.common.R<java.lang.String>
     * @Author: shuangleng
     * @Date: 2022/6/20 19:42
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        log.info("dishDto.toString()");

        dishServiceImp.saveWithFlavor(dishDto);
        return R.success("成功保存");

    }

    /***
     * @Description: 根据id查询菜品信息与口味信息
     * @Param: [id]
     * @return: com.shuangleng.reggie.common.R<com.shuangleng.reggie.dto.DishDto>
     * @Author: shuangleng
     * @Date: 2022/6/21 14:44
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {
        DishDto dishDto = dishServiceImp.getByIdWithFlavor(id);
        return R.success(dishDto);

    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        log.info("dishDto.toString()");
        dishServiceImp.updateWithFlavor(dishDto);
        //清理所有菜品的缓存数据
        //Set keys = redisTemplate.keys("dish_*");
        //redisTemplate.delete(keys);

        //清理某一分类下的数据
        String key = "dish_"+dishDto.getCategoryId();
        redisTemplate.delete(key);

        return R.success("成功保存");

    }

    /**
     * @Description: 批量或者单个停售菜品
     * @Param: [id, ids]
     * @return: com.shuangleng.reggie.common.R<java.util.List>
     * @Author: shuangleng
     * @Date: 2022/6/21 20:05
     */
    @PostMapping("/status/{id}")
    public R<List<Dish>> Stop(@PathVariable long id, @RequestParam List<Long> ids) {
        log.info(ids.toString());
        List<Dish> list = ids.stream().map((item) -> dishServiceImp.getById(item)).collect(Collectors.toList());
        if (id == 0) {
            for (Dish dish : list) {
                dish.setStatus(0);
            }
            //dish.setStatus(0);
        } else {
            for (Dish dish : list) {
                dish.setStatus(1);
            }
            //dish.setStatus(1);
        }
        dishServiceImp.updateBatchById(list);
        return R.success(list);
    }

    /**
     * @Description: 菜品的批量删除
     * @Param: [ids]
     * @return: com.shuangleng.reggie.common.R<java.lang.String>
     * @Author: shuangleng
     * @Date: 2022/6/21 20:12
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        dishServiceImp.removeByIds(ids);
        return R.success("删除成功");
    }

    /**
     * @Description: 返回套餐中的添加菜品
     * @Param: [categoryId]
     * @return: com.shuangleng.reggie.common.R<java.lang.String>
     * @Author: shuangleng
     * @Date: 2022/6/23 10:14
     */
    @GetMapping("/list")
    public R<List<Dish>> list(Dish dish) {
        String name = "dish_"+dish.getCategoryId();
        //如果缓存有则是直接返回
        List<Dish> o = (List<Dish>) redisTemplate.opsForValue().get(name);
        if (o != null) {
            return R.success(o);
        }
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(dish != null, Dish::getCategoryId, dish.getCategoryId());
        wrapper.eq(Dish::getStatus, 1);
        List<Dish> list = dishServiceImp.list(wrapper);
        //没有的话则是设置缓存存活时间，然后返回
        redisTemplate.opsForValue().set(name, list, 60, TimeUnit.MINUTES);
        return R.success(list);

    }


}
