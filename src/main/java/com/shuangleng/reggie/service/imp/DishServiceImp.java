package com.shuangleng.reggie.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuangleng.reggie.dto.DishDto;
import com.shuangleng.reggie.entity.Dish;
import com.shuangleng.reggie.entity.DishFlavor;
import com.shuangleng.reggie.mapper.DishMapper;
import com.shuangleng.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：shuangleng
 * @date ：Created in 2022/6/3 21:06
 * @description：
 */
@Slf4j
@Service
//多表操作，添加事务。

public class DishServiceImp extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorServiceImp dishFlavor;
    @Transactional   //多表操作开启事务
    public void saveWithFlavor(DishDto dishDto){

        //保存到dish
        this.save(dishDto);
        //LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        //lqw.eq(Dish::getName,)
        Long id = dishDto.getId();
        //把菜品中的id保存在dishDto（(Data Transfer Object）的数列flavor中的dishid
        List<DishFlavor> flavors = dishDto.getFlavors();
        List<DishFlavor> collect = flavors.stream().map((dishFlavor) -> {
            dishFlavor.setDishId(id);
            return dishFlavor;
        }).collect(Collectors.toList());
        //保存在flavo表中
        dishFlavor.saveBatch(collect);

    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        //查询菜品
        Dish dish = this.getById(id);
        //创建返回的dto
        DishDto dishDto = new DishDto();
        //将普通属性封装进dto
        BeanUtils.copyProperties(dish,dishDto);
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId,id);
        //将查询到的口味属性保存在dto中最后进行返回
        List<DishFlavor> list = dishFlavor.list(wrapper);
        dishDto.setFlavors(list);
        return dishDto;
    }
    @Transactional
    @Override
    public void updateWithFlavor(DishDto dishDto) {
        //将dish内数据更新 update () where id;
        this.updateById(dishDto);
        //将flavor中数据清除然后添加修改内容
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavor.remove(wrapper);
        //这里传过来的dishDto中还是没有dishid
        List<DishFlavor> flavors = dishDto.getFlavors();
        List<DishFlavor> list = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return  item;
        }).collect(Collectors.toList());
        dishFlavor.saveBatch(list);

    }
}
