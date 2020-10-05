package com.aiz.notify;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.aiz.notify.*.mapper")
@SpringBootApplication
public class AizNotifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(AizNotifyApplication.class, args);
    }

}
