package com.shuangleng.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuangleng.reggie.dto.SetmealDto;
import com.shuangleng.reggie.entity.Setmeal;

/**
 * @author ：shuangleng
 * @date ：Created in 2022/6/22 10:44
 * @description：
 */
public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);
}
