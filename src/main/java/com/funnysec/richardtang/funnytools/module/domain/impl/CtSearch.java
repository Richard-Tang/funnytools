package com.funnysec.richardtang.funnytools.module.domain.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.funnysec.richardtang.funnytools.constant.*;
import com.funnysec.richardtang.funnytools.constant.Character;
import com.funnysec.richardtang.funnytools.module.domain.AbstractDomainModuleProcessor;
import com.funnysec.richardtang.funnytools.module.domain.pipeline.DomainPipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Spider;

import java.util.List;

/**
 * Ct证书搜索模块
 * ctsearch.entrust.com
 *
 * @author RichardTang
 * @date 2020/4/10
 */
@Component
public class CtSearch extends AbstractDomainModuleProcessor {

    @Autowired
    private CtSearch(@Value("${project.ct-search}") String api) {
        super(Module.CT_SEARCH, api);
    }

    /**
     * 初始化操作
     */
    @Override
    public void start(String target) {
        Spider.create(this)
                .addUrl(String.format(api,target))
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
        findDomainToResult(page.getHtml());
    }
}