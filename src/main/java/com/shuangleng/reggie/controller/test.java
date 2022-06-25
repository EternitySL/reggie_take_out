package com.shuangleng.reggie.controller;

import java.io.*;

/**
 * 测试文件另外保存的区别
 * @author ：shuangleng
 * @date ：Created in 2022/6/13 15:50
 * @description：
 */
public class test {
    public static void main(String[] args) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File("D:\\_photo\\0c533217-9d0f-417c-a674-7643fb5a4624.jpg"));
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            new FileOutputStream("D:\\_photo\\0c533217-9d0f-417c-a674-7643fb5a4625.jpg",true);
            //PrintStream printStream = new PrintStream(new FileOutputStream("D:\\_photo\\0c533217-9d0f-417c-a674-7643fb5a4625.jpg"),true);
            PrintStream printStream = new PrintStream(new FileOutputStream("D:\\_photo\\0c533217-9d0f-417c-a674-7643fb5a4625.jpg"),true);
            byte[] bytes = new byte[1024];
            int len;
            while ((len = bufferedInputStream.read(bytes)) != -1) {
                printStream.write(bytes,0,len);
            }
            printStream.flush();
            bufferedInputStream.close();
            printStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
