package com.funnysec.richardtang.funnytools.module.domain.impl;

import cn.hutool.core.util.StrUtil;
import com.funnysec.richardtang.funnytools.constant.*;
import com.funnysec.richardtang.funnytools.constant.Character;
import com.funnysec.richardtang.funnytools.module.domain.AbstractDomainModuleProcessor;
import com.funnysec.richardtang.funnytools.module.domain.pipeline.DomainPipeline;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
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

    private Integer pageSize;

    private Integer pageIndex;

    private BaiduSearch(@Value("${project.baidu}") String api) {
        super(Type.TASK_DOMAIN_BAIDU_SEARCH, api);
        this.pageSize = 50;
        this.pageIndex = 1;
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
        Document parse = Jsoup.parse(page.getHtml().get());
        parse.select("a[class='c-showurl']").forEach(e -> {
            // 去除开头携带的协议
            String url = e.text().replace(Protocol.HTTP, Character.BLANK).replace(Protocol.HTTPS, Character.BLANK);
            // 去除域名后边的路径
            String domain = StrUtil.subBefore(url, Character.BACKSLASH, false);
            result.add(domain);
        });
        addNextPageUrl(parse, page);
    }

    /**
     * 添加下一页的Url,没有的话着不添加
     *
     * @param document JSoup解析后的文档对象
     * @param page     Page
     */
    private void addNextPageUrl(Document document, Page page) {
        int pn = (pageIndex++ * pageSize);
        // 操作是根据百度的页面进行解析的
        String pageHtml = document.getElementById("page").html();
        int index = pageHtml.indexOf(String.format("pn=%s", pn));
        if (index != -1) {
            page.addTargetRequest(String.format(api, pn, currentTask.getTarget()));
        }
    }
}
