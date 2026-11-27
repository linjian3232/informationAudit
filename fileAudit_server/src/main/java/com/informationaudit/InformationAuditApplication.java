package com.informationaudit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.informationaudit.dao")
public class InformationAuditApplication {
    public static void main(String[] args) {
        SpringApplication.run(InformationAuditApplication.class, args);
    }

}
