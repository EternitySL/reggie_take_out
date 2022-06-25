package com.shuangleng.reggie;

import com.shuangleng.reggie.controller.EmployeeController;
import com.shuangleng.reggie.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


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
