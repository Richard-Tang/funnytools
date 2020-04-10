package com.funnysec.richardtang.funnytools.task.processer;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.funnysec.richardtang.funnytools.constant.Character;
import com.funnysec.richardtang.funnytools.constant.Pipeline;
import com.funnysec.richardtang.funnytools.constant.Protocol;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.webmagic.Page;

import java.util.HashSet;
import java.util.List;

/**
 * WebMagic爬虫实现从页面中查找域名
 *
 * @author RichardTang
 * @date 2020/3/21
 */
public class DomainPageSearchProcesser extends BaseProcessor {

    private String regex;

    public DomainPageSearchProcesser(String target, String key) {
        super(target, key);
        List<String> split = StrUtil.splitTrim(target, Character.POINTER);
        this.regex = String.format("[a-z0-9A-Z.-]*\\.%s\\.%s",
                CollUtil.get(split, split.size() - 2), CollUtil.getLast(split));
    }

    @Override
    public void processWrapper(Page page) {
        Document html = Jsoup.parse(page.getHtml().get());
        result.addAll(page.getHtml().regex(regex).all());
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
}
