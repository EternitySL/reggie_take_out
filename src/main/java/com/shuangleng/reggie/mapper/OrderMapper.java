package com.shuangleng.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuangleng.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ：shuangleng
 * @date ：Created in 2022/6/23 16:20
 * @description：
 */
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
