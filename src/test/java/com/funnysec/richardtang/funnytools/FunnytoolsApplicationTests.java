package com.funnysec.richardtang.funnytools;

import com.funnysec.richardtang.funnytools.constant.Pipeline;
import com.funnysec.richardtang.funnytools.task.pipeline.DomainPipeline;
import com.funnysec.richardtang.funnytools.task.processer.BaiduSearchProcesser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FunnytoolsApplicationTests {

    @Value("${project.baidu-search}")
    private String baiduSearch;

    @Test
    void contextLoads() {
//        Spider.create(new BaiduSearchProcesser("hutool.cn",Pipeline.BAIDU_SEARCH_PIPELINE_KEY))
//                .addUrl(String.format(baiduSearch,"0","hutool.cn"))
//                .addPipeline(new DomainPipeline(Pipeline.BAIDU_SEARCH_PIPELINE_KEY))
//                .run();
    }
}
