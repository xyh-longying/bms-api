package com.longying.bms.usermis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * scanBasePackages增加扫描路径，方便注入bean
 */
@SpringBootApplication(scanBasePackages = {"com.longying"})
public class UserMisApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserMisApplication.class, args);
    }

}
