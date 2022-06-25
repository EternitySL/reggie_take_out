package com.shuangleng.reggie.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuangleng.reggie.entity.User;
import com.shuangleng.reggie.mapper.UserMapper;
import com.shuangleng.reggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ：shuangleng
 * @date ：Created in 2022/6/24 15:14
 * @description：
 */
@Service
@Slf4j
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {
}
