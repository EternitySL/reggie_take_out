package com.shuangleng.reggie.common;

/**
 * @author ：shuangleng
 * @date ：Created in 2022/6/1 15:18
 * @description：同线程保存数据
 */
public class BaseContext {
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
