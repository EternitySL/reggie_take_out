package com.shuangleng.reggie.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuangleng.reggie.dto.SetmealDto;
import com.shuangleng.reggie.entity.Setmeal;
import com.shuangleng.reggie.entity.SetmealDish;
import com.shuangleng.reggie.mapper.SetmealMapper;
import com.shuangleng.reggie.service.SetmealDishService;
import com.shuangleng.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：shuangleng
 * @date ：Created in 2022/6/22 10:46
 * @description：
 */
@Service
@Slf4j
public class SetmealServiceImp extends ServiceImpl<SetmealMapper , Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishServiceImp setmealDishServiceImp;
    /**
    * @Description: 多表操作，用DTO同时保存到setmeal与setmealdish两张表。
    * @Param: [setmealDto]
    * @return: void
    * @Author: shuangleng
    * @Date: 2022/6/23 16:07
    */
    @Override
    @Transactional //多表操作打开事务
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);
        Long id = setmealDto.getId();
        List<SetmealDish> list = setmealDto.getSetmealDishes();
        List<SetmealDish> list1 = list.stream().map((item) -> {
            item.setSetmealId(id);
            return item;
        }).collect(Collectors.toList());
        setmealDishServiceImp.saveBatch(list1);
    }
}
