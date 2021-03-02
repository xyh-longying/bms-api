package com.longying.bms.systemmis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * scanBasePackages增加扫描路径，方便注入bean
 */
@SpringBootApplication(scanBasePackages = {"com.longying"})
public class SystemMisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemMisApplication.class, args);
    }

}
