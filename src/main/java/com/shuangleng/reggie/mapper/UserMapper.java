package com.shuangleng.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuangleng.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ：shuangleng
 * @date ：Created in 2022/6/24 15:13
 * @description：
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
