package com.zzjz.zzjg.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置静态资源映射
 * @author sam
 * @since 2017/7/16
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        //addResourceLocations的每一个值必须以'/'结尾,否则虽然映射了,但是依然无法访问该目录下的的文件(支持: classpath:/xxx/xx/, file:/xxx/xx/, http://xxx/xx/)
        //这里外部资源指定的是相对目录，当然也可以指定绝对目录如.addResourceLocations("file:E:/cxy/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/", "classpath:/templates/", "file:static/");
    }
}
