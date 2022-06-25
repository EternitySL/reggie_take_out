package com.shuangleng.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuangleng.reggie.common.R;
import com.shuangleng.reggie.entity.Employee;
import com.shuangleng.reggie.mapper.EmployeeMapper;
import com.shuangleng.reggie.service.imp.EmployeeSeviceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController   //相当于respondbody加controller
@Slf4j
@RequestMapping("/employee")    //前缀
@ServletComponentScan("com/shuangleng/reggie")  //扫描filter拦截器路径
public class EmployeeController {
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    public EmployeeSeviceImp employeeSeviceImp;
    //登录比对后加入session中id
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        R r = employeeSeviceImp.Md5(employee);
        //这里加入的是查询后的数据，之前进来的数据没有id属性
        Employee data = (Employee) r.getData();
        request.getSession().setAttribute("employee",data.getId());
        return r;
    }
    //退出后，移除session中数据
    @PostMapping("/logout")
    public R<String> loginOut(HttpServletRequest request) {
        //去除session
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
    //增加员工
    @PostMapping
    public R<Employee> save(HttpServletRequest request, @RequestBody Employee employee){
        R r = employeeSeviceImp.add(employee);
        Employee data =(Employee) r.getData();
        Long empl =(Long) request.getSession().getAttribute("employee");
        employeeSeviceImp.save(data);
        return r;
    }
    //单页查询
    @GetMapping("/page")
    public R<Page> page(int page ,int pageSize,String name){
        log.info("page={},pageSize={},name={}",page,pageSize,name);
        Page page1 = new Page<>(page, pageSize);
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        //name进行like模糊查询
        lqw.like(name != null,Employee::getName,name);
        lqw.orderByAsc(Employee::getUpdateTime);
        Page iPage = employeeMapper.selectPage(page1, lqw);
        return R.success(iPage);
    }
    /**
    * @Description: 根据id修改员工信息，包括state禁用状态
    * @Param: [request, employee]
    * @return: com.shuangleng.reggie.common.R<java.lang.String>
    * @Author: shuangleng
    * @Date: 2022/5/30 15:54
    */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        log.info(employee.toString());
        //Long employee1 =(Long) request.getSession().getAttribute("employee");
        //employee.setUpdateTime(LocalDateTime.now());
        //employee.setUpdateUser(employee1);
        //响应体自带employee中有已经修改了
        employeeSeviceImp.updateById(employee);
        return R.success("修改成功");
    }
    /**
    * @Description: 返回id对应的员工数据
    * @Param: [id]
    * @return: com.shuangleng.reggie.common.R<com.shuangleng.reggie.entity.Employee>
    * @Author: shuangleng
    * @Date: 2022/5/31 16:04
    */
    @GetMapping("/{id}")
    public R<Employee> getId(@PathVariable String id){

        log.info("查询id员工信息");
        Employee byId = employeeSeviceImp.getById(id);
        if(byId != null){
            return R.success(byId);
        }else {
            return R.error("查询错误");
        }
    }


}
