package com.shuangleng.reggie.controller;

import com.shuangleng.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.Model;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * @author ：shuangleng
 * @date ：Created in 2022/6/5 9:59
 * @description：文件上传接收
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${reggie.path}")
    private String basePath;

    @PostMapping("/upload")
    //这里的multipart是springweb提供的文件形式
    //file名字与前端名字和相同，且这里是临时文件需要转存
    public R<String> upload(MultipartFile file) {
        log.info(file.toString());
        //原始文件名字
        String originalFilename = file.getOriginalFilename(); //122.jpg
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));//.jpg后缀
        //使用UUID生成随机名字防止覆盖
        String s = UUID.randomUUID() + suffix;  //wrqerqwe  +  .jpg
        File file1 = new File(basePath);
        if (!file1.exists()) {
            //    目录不存在需要创建
            file1.mkdirs();
        }
        try {
            log.info(s);
            file.transferTo(new File(basePath + s));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(s);
    }

    @GetMapping("/download")
    public void downLoad(String name, HttpServletResponse response) {
        try {
            FileInputStream fileInputStream = new FileInputStream(basePath + name);
            response.setContentType("image/jpeg");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

            ServletOutputStream outputStream = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int len;
            while ((len = bufferedInputStream.read(bytes)) != -1) {
                outputStream.write(bytes,0,len);
            }
            outputStream.flush();
            outputStream.close();
            bufferedInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
