package com.shuangleng.reggie.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuangleng.reggie.common.R;
import com.shuangleng.reggie.entity.Employee;
import com.shuangleng.reggie.mapper.EmployeeMapper;
import com.shuangleng.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Slf4j
@Service
public class EmployeeSeviceImp extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    private EmployeeSeviceImp employeeSeviceImp;
    //用户名密码验证

    /**
     * @Description: 登录账号，这里是将密码进行MD5加密后与数据库比对
     * @Param: [employee]
     * @return: com.shuangleng.reggie.commer.R
     * @Author: shuangleng
     * @Date: 2022/5/26 15:20
     */
    public R Md5(Employee employee) {
        //将数据MD5加密
        String password = employee.getPassword();
        byte[] bytes = password.getBytes();
        String password1 = DigestUtils.appendMd5DigestAsHex( bytes, new StringBuilder()).toString();
//        查询映射
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Employee::getUsername, employee.getUsername());
        Employee one = employeeSeviceImp.getOne(lqw);
//        进行校验
        if (one == null) {
            return R.error("登录错误");
        }
        if (!one.getPassword().equals(password1)) {
            return R.error("密码错误");
        }
        if (one.getStatus() == 0) {  //状态码错误
            return R.error("账号禁用");
        }
        return R.success(one);
    }

    /**
     * @Description: 新增加员工
     * @Param: [employee]
     * @return: com.shuangleng.reggie.common.R
     * @Author: shuangleng
     * @Date: 2022/5/27 18:03
     */
    public R add( Employee employee) {
        log.info("员工信息{}", employee.toString());

        long id = Thread.currentThread().getId();
        log.info("生成的id={}",id);
        //employee.setCreateTime(LocalDateTime.now());
        //employee.setUpdateTime(LocalDateTime.now());
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
       return R.success(employee);
    }


}
