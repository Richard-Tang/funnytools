package com.funnysec.richardtang.funnytools.config;

import com.funnysec.richardtang.funnytools.entity.System;
import com.funnysec.richardtang.funnytools.service.ISystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 系统环境初始化
 *
 * @author RichardTang
 * @date 2020/4/7
 */
@Configuration
public class SystemConfig {

    @Autowired
    private ISystemService systemService;

    /**
     * 系统配置
     */
    @Bean
    public System system() {
        return systemService.getById(1);
    }
}
