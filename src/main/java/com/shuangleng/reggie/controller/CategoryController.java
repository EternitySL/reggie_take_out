package com.shuangleng.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuangleng.reggie.common.R;
import com.shuangleng.reggie.entity.Category;
import com.shuangleng.reggie.service.CategoryService;
import com.shuangleng.reggie.service.imp.CategoryServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;

/**
 * @author ：shuangleng
 * @date ：Created in 2022/6/1 14:49
 * @description：
 */
@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    public CategoryServiceImp categoryServiceImp;
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){
        Page<Category> categoryPage = new Page<>(page,pageSize);
        LambdaQueryWrapper<Category> lqw= new LambdaQueryWrapper<>();
        lqw.orderByAsc(Category::getSort);
        Page<Category> page1 = categoryServiceImp.page(categoryPage, lqw);
        return R.success(page1);
    }
    @PostMapping
    public R<String> save(@RequestBody Category category){
        categoryServiceImp.save(category);
        return R.success("保存成功");
    }
    @DeleteMapping
    public R<String> delete(String ids){
        log.info("删除菜品");
        categoryServiceImp.removeById(ids);
        return R.success("删除成功");
    }
    @PutMapping
    public R<Category> change(@RequestBody Category category){
        categoryServiceImp.updateById(category);
        return R.success(category);
    }
    /***
    * @Description: 下拉查询菜品
    * @Param: [category]
    * @return: com.shuangleng.reggie.common.R<java.util.List<com.shuangleng.reggie.entity.Category>>
    * @Author: shuangleng
    * @Date: 2022/6/20 19:42
    */
    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.eq(category.getType() != null,Category::getType,category.getType());
        lqw.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryServiceImp.list(lqw);
        return R.success(list);
    }



}
