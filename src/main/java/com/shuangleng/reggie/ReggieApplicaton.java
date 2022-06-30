package com.shuangleng.reggie;

import com.shuangleng.reggie.controller.EmployeeController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@SpringBootApplication
//boot自动开启
//@EnableTransactionManagement
public class  ReggieApplicaton {
    private EmployeeController employeeController;
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplicaton.class, args);


    }
}
