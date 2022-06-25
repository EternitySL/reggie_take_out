package com.shuangleng.reggie.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuangleng.reggie.entity.Orders;
import com.shuangleng.reggie.mapper.OrderMapper;
import com.shuangleng.reggie.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * @author ：shuangleng
 * @date ：Created in 2022/6/23 16:22
 * @description：
 */
@Service
public class OrderServiceImp extends ServiceImpl<OrderMapper, Orders> implements OrderService {
}
