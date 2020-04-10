package com.funnysec.richardtang.funnytools.task.processer;

import cn.hutool.core.util.StrUtil;
import com.funnysec.richardtang.funnytools.constant.Character;
import com.funnysec.richardtang.funnytools.constant.Protocol;
import com.funnysec.richardtang.funnytools.task.ini.DomainBaiduSearchIni;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import us.codecraft.webmagic.Page;

/**
 * WebMagic爬虫实现从Baidu搜索页面中查找域名
 *
 * @author RichardTang
 * @date 2020/3/21
 */
public class BaiduSearchProcesser extends BaseProcessor {

    /**
     * 百度搜索引擎每页显示条数
     */
    private Integer pageSize;

    /**
     * 页号
     */
    private Integer pageIndex;

    private String api;

    public BaiduSearchProcesser(String target, String key, String api) {
        super(target, key);
        this.pageSize = 50;
        this.pageIndex = 1;
        this.api = api;
    }

    @Override
    public void processWrapper(Page page) {
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
            page.addTargetRequest(String.format(api, pn, this.target));
        }
    }

}
