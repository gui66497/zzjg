package com.zzjz.zzjg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.zzjz.zzjg.mapper")
@SpringBootApplication
public class ZzjgApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZzjgApplication.class, args);
    }

}
