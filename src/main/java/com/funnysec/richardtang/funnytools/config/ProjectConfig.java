package com.funnysec.richardtang.funnytools.config;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.funnysec.richardtang.funnytools.constant.State;
import com.funnysec.richardtang.funnytools.entity.Config;
import com.funnysec.richardtang.funnytools.entity.System;
import com.funnysec.richardtang.funnytools.service.IConfigService;
import com.funnysec.richardtang.funnytools.service.ISystemService;
import com.funnysec.richardtang.funnytools.service.ITaskService;
import com.funnysec.richardtang.funnytools.module.domain.ini.DomainModuleDicFuzzIni;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 系统环境初始化
 *
 * @author RichardTang
 * @date 2020/4/7
 */
@Configuration
public class ProjectConfig implements CommandLineRunner {

    @Autowired
    private ITaskService taskService;

    @Autowired
    private IConfigService configService;

    @Autowired
    private ISystemService systemService;

    /**
     * 系统配置
     */
    @Bean
    public System system() {
        return systemService.getById(1);
    }

    @Bean
    public DomainModuleDicFuzzIni domainModuleDicFuzzIni() {
        Config config = configService.query().eq("type", 0).one();
        if (ObjectUtil.isNull(config) || StrUtil.isEmpty(config.getIni())) {
            return new DomainModuleDicFuzzIni(20);
        }
        return JSONUtil.toBean(config.getIni(), DomainModuleDicFuzzIni.class);
    }

    @Override
    public void run(String... args) throws Exception {
        taskService.update().eq("state", State.TASK_ING).set("state", State.TASK_WAIT);
    }
}
