package com.shuangleng.reggie.common;

import com.shuangleng.reggie.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.sound.sampled.Line;
import java.sql.SQLClientInfoException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author ：shuangleng
 * @date ：Created in 2022/5/27 21:09
 * @description：处理全局异常
 */
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
@Slf4j

public class GloberExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<Employee> exceptionHander(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());
        return R.error("失败了");
    }
}
