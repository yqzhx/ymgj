package com.uustop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author uustop
 */

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@tk.mybatis.spring.annotation.MapperScan("com.uustop.project.*.*.mapper")
@MapperScan("com.uustop.project.*.*.mapper")
public class UUSTOPApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(UUSTOPApplication.class, args);
    }
}