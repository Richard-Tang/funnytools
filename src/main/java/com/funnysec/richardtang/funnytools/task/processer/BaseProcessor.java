package com.funnysec.richardtang.funnytools.task.processer;

import com.funnysec.richardtang.funnytools.constant.HttpHeaders;
import com.funnysec.richardtang.funnytools.utils.NetUtils;
import com.funnysec.richardtang.funnytools.utils.UserAgentUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.HashSet;

/**
 * 定义爬虫统一的共性行为
 *
 * @author RichardTang
 * @date 2020/4/9
 */
public abstract class BaseProcessor implements PageProcessor {

    /**
     * 这个key是用来和Pipeline中进行绑定的
     */
    protected String key;

    /**
     * 目标域名
     */
    protected String target;

    /**
     * 域名对象
     */
    protected Site site = Site.me();

    /**
     * 存储结果的集合
     */
    protected HashSet<String> result;

    protected BaseProcessor(String target, String key) {
        this.key = key;
        this.target = target;
        this.result = new HashSet<String>();
    }

    @Override
    public void process(Page page) {
        page.putField(key, result);
        processWrapper(page);
    }

    public abstract void processWrapper(Page page);

    @Override
    public Site getSite() {
        String ip = NetUtils.getRandomIp();
        return site
                .setUserAgent(UserAgentUtil.random())
                .addHeader(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .addHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate, b")
                .addHeader(HttpHeaders.ACCEPT_LANGUAGE, "en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7")
                .addHeader(HttpHeaders.CACHE_CONTROL, "max-age=0")
                .addHeader(HttpHeaders.CONNECTION, HttpHeaders.CONNECTION_KEEP_ALIVE)
                .addHeader(HttpHeaders.REFERER, HttpHeaders.REFERER_GOOGLE)
                .addHeader(HttpHeaders.DNT, "1")
                .addHeader(HttpHeaders.UPGRADE_INSECURE_REQUESTS, "1")
                .addHeader(HttpHeaders.X_FORWARDED_FOR, ip)
                .addHeader(HttpHeaders.X_FORWARDED_HOST, ip)
                .addHeader(HttpHeaders.X_CLIENT_IP, ip)
                .addHeader(HttpHeaders.X_REMOTE_IP, ip)
                .addHeader(HttpHeaders.X_REMOTE_ADDR, ip)
                .addHeader(HttpHeaders.TRUE_CLIENT_IP, ip)
                .addHeader(HttpHeaders.CLIENT_IP, ip)
                .addHeader(HttpHeaders.X_REAL_IP, ip)
                .addHeader(HttpHeaders.ALI_CDN_REAL_IP, ip)
                .addHeader(HttpHeaders.CDN_SRC_IP, ip);
    }
}
