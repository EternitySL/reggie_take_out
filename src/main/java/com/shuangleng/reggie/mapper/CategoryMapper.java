package com.shuangleng.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuangleng.reggie.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ：shuangleng
 * @date ：Created in 2022/6/1 14:43
 * @description：菜品分类
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
