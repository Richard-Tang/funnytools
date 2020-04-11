package com.funnysec.richardtang.funnytools.module.domain.impl;

import com.funnysec.richardtang.funnytools.constant.*;
import com.funnysec.richardtang.funnytools.module.domain.AbstractDomainModuleProcessor;
import com.funnysec.richardtang.funnytools.module.domain.pipeline.DomainPipeline;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Spider;

/**
 * 百度搜索域名模块
 *
 * @author RichardTang
 * @date 2020/4/10
 */
@Component
public class BaiduSearch extends AbstractDomainModuleProcessor {

    private Integer pageIndex = 1;

    private BaiduSearch(@Value("${project.baidu-search}") String api) {
        super(Module.BAIDU_SEARCH, api);
    }

    /**
     * 初始化操作
     */
    @Override
    public void start(String target) {
        Spider.create(this)
                .addUrl(String.format(api, "0", target))
                .addPipeline(new DomainPipeline())
                .run();
    }

    /**
     * /**
     * WebMagic提供的处理函数
     *
     * @param page 页面对象
     */
    @Override
    public void process(Page page) {
        page.putField(DomainPipeline.KEY, result);
        findDomainToResult(page.getHtml());
        addNextPageUrl(page);
    }

    /**
     * 添加下一页的Url,没有的话着不添加
     *
     * @param page     Page
     */
    private void addNextPageUrl(Page page) {
        int pn = (pageIndex++ * 50);
        // 操作是根据百度的页面进行解析的
        String pageHtml = page.getHtml().getDocument().getElementById("page").html();
        int index = pageHtml.indexOf(String.format("pn=%s", pn));
        if (index != -1) {
            page.addTargetRequest(String.format(api, pn, currentTask.getTarget()));
        }
    }
}
