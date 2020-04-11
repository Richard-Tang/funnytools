package com.funnysec.richardtang.funnytools.module.domain.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.funnysec.richardtang.funnytools.constant.*;
import com.funnysec.richardtang.funnytools.constant.Character;
import com.funnysec.richardtang.funnytools.module.domain.AbstractDomainModuleProcessor;
import com.funnysec.richardtang.funnytools.module.domain.pipeline.DomainPipeline;
import com.funnysec.richardtang.funnytools.utils.NetUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Spider;

import java.util.List;

/**
 * 页面搜索模块
 * 在页面的Html,Js文件中搜索域名
 *
 * @author RichardTang
 * @date 2020/4/10
 */
@Component
public class PageSearch extends AbstractDomainModuleProcessor {

    @Autowired
    private PageSearch() {
        super(Module.PAGE_SEARCH);
    }

    /**
     * 初始化操作
     */
    @Override
    public void start(String target) {
        String url = NetUtils.getHttpProtocol(target) + target;
        Spider.create(this)
                .addUrl(url)
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
        Document html = page.getHtml().getDocument();
        List<String> jsLink = html.getElementsByTag("script").eachAttr("src");
        jsLink.forEach(link -> {
            // 非 ['https://','http://','//'] 开头的连接一定都为当前域名下的资源文件
            if (!StrUtil.startWithAny(link, Protocol.HTTPS, Protocol.HTTP, Protocol.HTTP_AUTO)) {
                int index = link.indexOf(Character.BACKSLASH) + 1;
                link = String.format("%s%s/%s", Protocol.HTTP, currentTask.getTarget(), link.substring(index));
            }
            page.addTargetRequest(link);
        });
    }
}
