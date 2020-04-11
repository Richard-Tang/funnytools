package com.funnysec.richardtang.funnytools.module.domain.impl;

import cn.hutool.core.util.StrUtil;
import com.funnysec.richardtang.funnytools.constant.Module;
import com.funnysec.richardtang.funnytools.module.domain.AbstractDomainModuleProcessor;
import com.funnysec.richardtang.funnytools.module.domain.pipeline.DomainPipeline;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Spider;

import java.util.List;

/**
 * 必应搜索引擎爬取
 *
 * @author RichardTang
 * @date 2020/4/11
 */
@Component
public class BingSearch extends AbstractDomainModuleProcessor {

    private Integer pageIndex = 0;

    protected BingSearch(@Value("${project.bing-search}") String api) {
        super(Module.BING_SEARCH, api);
    }

    @Override
    public void process(Page page) {
        page.putField(DomainPipeline.KEY, result);
        findDomainToResult(page.getHtml());
        Elements swNext = page.getHtml().getDocument().select(".sw_next");
        if (!swNext.isEmpty()) {
            pageIndex += 50;
            page.addTargetRequest(String.format(api, currentTask.getTarget(), pageIndex));
        }
    }

    @Override
    protected void start(String target) {
        Spider.create(this)
                .addUrl(String.format(api, target, pageIndex))
                .addPipeline(new DomainPipeline())
                .run();
    }
}
