package com.hujunchina;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author hujunchina@outlook.com
 */
@SpringBootApplication
public class SpringStarter {

    private static final Logger logger = LoggerFactory.getLogger(SpringStarter.class);

    public static void main(String[] args) {
        Long startTime = System.currentTimeMillis();
        SpringApplication.run(SpringStarter.class, args);
        Long endTime = System.currentTimeMillis();
        logger.info("Spring 启动成功：用时：{} 秒", (endTime-startTime)/60);

    }
}
