package com.zzjz.zzjg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.Collections;

/**
 * @ClassName: SwaggerConfig
 * @Description: Swagger配置类
 * @author 房桂堂
 * @date 2019/7/5 15:52
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "二期改造项目API文档",
                "后台访问地址：http://192.168.1.129:8080/zzjg",
                "API V0.1",
                "",
                new Contact("", "", ""),
                "", "http://www.apache.org/", Collections.emptyList());
    }
}