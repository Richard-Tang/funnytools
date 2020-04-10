package com.funnysec.richardtang.funnytools;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 启动类
 *
 * @author RichardTang
 * @date 2020年3月16日18:27:30
 */
@EnableSwagger2
@EnableScheduling
//@EnableTransactionManagement
@EnableConfigurationProperties
@MapperScan("com.funnysec.richardtang.funnytools.mapper*")
@Import(cn.hutool.extra.spring.SpringUtil.class)
@SpringBootApplication
public class FunnyToolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FunnyToolsApplication.class, args);
    }

}
