package com.zhd.emb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zhd.emb.mapper")
public class EmbApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmbApplication.class, args);
    }

}
