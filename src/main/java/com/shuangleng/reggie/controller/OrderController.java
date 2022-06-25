package com.shuangleng.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuangleng.reggie.common.R;
import com.shuangleng.reggie.entity.Orders;
import com.shuangleng.reggie.service.imp.OrderServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author ：shuangleng
 * @date ：Created in 2022/6/23 16:25
 * @description：
 */
@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderServiceImp orderServiceImp;
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String number, LocalDateTime beginTime ,LocalDateTime endTime){
        Page<Orders> ordersPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(number != null,Orders::getNumber,number);
        wrapper.between(Orders::getOrderTime,beginTime,endTime);
        orderServiceImp.page(ordersPage,wrapper);
        return R.success(ordersPage);
    }
}
