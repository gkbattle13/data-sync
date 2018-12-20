package com.incar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2018/12/17 13:49
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableScheduling
public class App  {
    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

}
