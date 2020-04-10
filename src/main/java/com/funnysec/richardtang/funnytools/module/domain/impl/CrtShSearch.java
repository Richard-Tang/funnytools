package com.funnysec.richardtang.funnytools.module.domain.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.funnysec.richardtang.funnytools.constant.*;
import com.funnysec.richardtang.funnytools.constant.Character;
import com.funnysec.richardtang.funnytools.module.domain.AbstractDomainModuleProcessor;
import com.funnysec.richardtang.funnytools.module.domain.pipeline.DomainPipeline;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Spider;

import java.util.List;

/**
 * CrtSh证书搜索模块
 * crt.sh
 *
 * @author RichardTang
 * @date 2020/4/10
 */
@Component
public class CrtShSearch extends AbstractDomainModuleProcessor {

    private CrtShSearch(@Value("${project.crt-sh}") String api) {
        super(Type.TASK_DOMAIN_CERT_SEARCH, api);
    }

    /**
     * 初始化操作
     */
    @Override
    public void start(String target) {
        try {
            Thread.sleep(10000);
        } catch (Exception e) {

        }
        List<String> split = StrUtil.splitTrim(target, Character.POINTER);
        this.regex = String.format("[a-z0-9A-Z.-]*\\.%s\\.%s",
                CollUtil.get(split, split.size() - 2), CollUtil.getLast(split));
        Spider.create(this)
                .addUrl((api + target).replace("*.", Character.BLANK))
                .addPipeline(new DomainPipeline())
                .run();
    }

    /**
     * WebMagic提供的处理函数
     *
     * @param page 页面对象
     */
    @Override
    public void process(Page page) {
        page.putField(DomainPipeline.KEY, result);
        ReUtil.findAll(regex, page.getHtml().get(), 0, result);
    }
}
