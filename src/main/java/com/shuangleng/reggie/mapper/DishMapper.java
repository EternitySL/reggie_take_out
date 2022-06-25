package com.shuangleng.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuangleng.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author ：shuangleng
 * @date ：Created in 2022/6/3 20:54
 * @description：
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
