package com.shuangleng.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuangleng.reggie.common.R;
import com.shuangleng.reggie.dto.SetmealDto;
import com.shuangleng.reggie.entity.Dish;
import com.shuangleng.reggie.entity.Setmeal;
import com.shuangleng.reggie.entity.SetmealDish;
import com.shuangleng.reggie.service.imp.SetmealDishServiceImp;
import com.shuangleng.reggie.service.imp.SetmealServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：shuangleng
 * @date ：Created in 2022/6/22 10:53
 * @description：
 */
@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealDishServiceImp setmealDishServiceImp;
    @Autowired
    private SetmealServiceImp setmealServiceImp;
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page<Setmeal> page1 = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null,Setmeal::getName,name);
        setmealServiceImp.page(page1,wrapper);
        return R.success(page1);
    }
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealServiceImp.saveWithDish(setmealDto);
        return R.success("保存成功");
    }
    /**
     * @Description: 批量或者单个停售套餐
     * @Param: [id, ids]
     * @return: com.shuangleng.reggie.common.R<java.util.List>
     * @Author: shuangleng
     * @Date: 2022/6/21 20:05
     */
    @PostMapping("/status/{id}")
    public R<List<Setmeal>> Stop(@PathVariable long id, @RequestParam List<Long> ids) {
        log.info(ids.toString());
        List<Setmeal> list = ids.stream().map((item) -> setmealServiceImp.getById(item)).collect(Collectors.toList());
        if (id == 0) {
            for (Setmeal setmeal : list) {
                setmeal.setStatus(0);
            }
            //dish.setStatus(0);
        } else {
            for (Setmeal setmeal : list) {
                setmeal.setStatus(1);
            }
            //dish.setStatus(1);
        }
        setmealServiceImp.updateBatchById(list);
        return R.success(list);
    }

    /**
     * @Description: 套餐的批量删除
     * @Param: [ids]
     * @return: com.shuangleng.reggie.common.R<java.lang.String>
     * @Author: shuangleng
     * @Date: 2022/6/21 20:12
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        setmealServiceImp.removeByIds(ids);
        return R.success("删除成功");
    }
}
