package com.funnysec.richardtang.funnytools.task.processer;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.funnysec.richardtang.funnytools.constant.Character;
import com.funnysec.richardtang.funnytools.constant.Protocol;
import com.funnysec.richardtang.funnytools.entity.Domain;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.HashSet;
import java.util.List;

/**
 * WebMagic爬虫实现从页面中查找域名
 *
 * @author RichardTang
 * @date 2020/3/21
 */
public class DomainPageSearchProcesser implements PageProcessor {

    private String regex;

    private String target;

    private Site site = Site.me();

    public DomainPageSearchProcesser(String target) {
        this.target = target;
        List<String> split = StrUtil.splitTrim(target, Character.POINTER);
        this.regex = String.format("[a-z0-9A-Z.-]*\\.%s\\.%s", CollUtil.get(split, split.size() - 2), CollUtil.getLast(split));
    }

    @Override
    public void process(Page page) {
        Document html = Jsoup.parse(page.getHtml().get());
        HashSet<String> domains = new HashSet<String>(page.getHtml().regex(regex).all());
        page.putField("DomainPageSearchProcesser", domains);
        List<String> jsLink = html.getElementsByTag("script").eachAttr("src");
        jsLink.forEach(link -> {
            // 非 ['https://','http://','//'] 开头的连接一定都为当前域名下的资源文件
            if (!StrUtil.startWithAny(link, Protocol.HTTPS, Protocol.HTTP, Protocol.HTTP_AUTO)) {
                int index = link.indexOf(Character.BACKSLASH) + 1;
                link = String.format("%s%s/%s", Protocol.HTTP, target, link.substring(index));
            }
            page.addTargetRequest(link);
        });
    }

    @Override
    public Site getSite() {
        return site;
    }
}
