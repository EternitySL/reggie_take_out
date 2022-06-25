package com.shuangleng.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author ：shuangleng
 * @date ：Created in 2022/5/31 17:13
 * @description：填入注解中fill中的数据
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动生成[inset]");
        //判断当前是不是同一个线程，可以用ThreadLocal来保存数据
        long id = Thread.currentThread().getId();
        log.info("生成的id={}",id);
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        //登录保存登录着的id
        metaObject.setValue("createUser",BaseContext.getCurrentId());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动生成[upset]");
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
        metaObject.setValue("updateTime", LocalDateTime.now());
    }
}
