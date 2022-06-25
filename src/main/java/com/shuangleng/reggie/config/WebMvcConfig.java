package com.shuangleng.reggie.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;
/**
 * @author ：shuangleng
 * @date ：Created in 2022/5/27 21:59
 * @description：mvc放行静态资源
 */
//@Configuration
//public class WebMvcConfig extends WebMvcConfigurationSupport {
//    protected void  addResourceHandle(ResourceHandlerRegistry registry){
//        registry.addResourceHandler().addResourceLocations();
//
//    }
//}

@Configuration
public class WebMvcConfig {
    /**
    * @Description: 设置longid返回为String，防止精度丢失
    * @Param: [builder]
    * @return: com.fasterxml.jackson.databind.ObjectMapper
    * @Author: shuangleng
    * @Date: 2022/5/31 16:02
    */

    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder)
    {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();

        // 全局配置序列化返回 JSON 处理
        SimpleModule simpleModule = new SimpleModule();
        //JSON Long ==> String
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

}
