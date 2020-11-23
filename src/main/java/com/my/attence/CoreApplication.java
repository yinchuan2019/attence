package com.my.attence;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.sql.DataSource;

@SpringBootApplication
@MapperScan("com.my.attence.mapper")
public class CoreApplication{
    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }
}
