package com.shuangleng.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuangleng.reggie.dto.DishDto;
import com.shuangleng.reggie.entity.Dish;

/**
 * @author ：shuangleng
 * @date ：Created in 2022/6/3 21:05
 * @description：
 */

public interface DishService extends IService<Dish> {
     void saveWithFlavor(DishDto dishDto);
     DishDto getByIdWithFlavor(Long id);
    void updateWithFlavor(DishDto dishDto);
}
