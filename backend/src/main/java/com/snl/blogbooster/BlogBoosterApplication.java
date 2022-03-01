package com.snl.blogbooster;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@MapperScan(basePackages = "com.snl.blogbooster")
@EnableJpaAuditing
@SpringBootApplication
public class BlogBoosterApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogBoosterApplication.class, args);
    }

}
