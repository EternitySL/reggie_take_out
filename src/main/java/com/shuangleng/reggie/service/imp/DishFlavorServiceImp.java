package com.shuangleng.reggie.service.imp;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuangleng.reggie.entity.DishFlavor;
import com.shuangleng.reggie.mapper.DishFlavorMapper;
import com.shuangleng.reggie.service.DishFlavorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ：shuangleng
 * @date ：Created in 2022/6/20 17:16
 * @description：
 */
@Service
@Slf4j
public class DishFlavorServiceImp extends ServiceImpl<DishFlavorMapper, DishFlavor>implements DishFlavorService {
}
