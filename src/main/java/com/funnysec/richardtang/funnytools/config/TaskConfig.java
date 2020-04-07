package com.funnysec.richardtang.funnytools.config;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.funnysec.richardtang.funnytools.constant.TaskState;
import com.funnysec.richardtang.funnytools.entity.Config;
import com.funnysec.richardtang.funnytools.service.IConfigService;
import com.funnysec.richardtang.funnytools.service.ITaskService;
import com.funnysec.richardtang.funnytools.task.ini.DomainCertSearchIni;
import com.funnysec.richardtang.funnytools.task.ini.DomainDicFuzzIni;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 初始化任务配置环境
 *
 * @author RichardTang
 * @date 2020/3/17
 */
@Configuration
public class TaskConfig implements CommandLineRunner {

    @Autowired
    private IConfigService configService;

    @Autowired
    private ITaskService taskService;

    @Bean
    public DomainDicFuzzIni domainDicFuzzIni() {
        Config config = configService.query().eq("type", 0).one();
        if (ObjectUtil.isNull(config) || StrUtil.isEmpty(config.getIni())) {
            return new DomainDicFuzzIni(20);
        }
        return JSONUtil.toBean(config.getIni(), DomainDicFuzzIni.class);
    }

    @Bean
    public DomainCertSearchIni domainCertSearchIni(@Value("${project.crt-sh-api}") String crtShApi,
                                                   @Value("${project.ct-search-api}") String ctSearchApi) {
        return new DomainCertSearchIni(crtShApi, ctSearchApi);
    }

    @Override
    public void run(String... args) {
        taskService.update().eq("state", TaskState.ING).set("state", TaskState.WAIT);
    }
}
