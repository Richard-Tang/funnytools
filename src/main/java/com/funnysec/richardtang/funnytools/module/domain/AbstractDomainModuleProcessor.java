package com.funnysec.richardtang.funnytools.module.domain;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.funnysec.richardtang.funnytools.constant.HttpHeaders;
import com.funnysec.richardtang.funnytools.utils.NetUtils;
import com.funnysec.richardtang.funnytools.utils.UserAgentUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.HashSet;
import java.util.List;

/**
 * 域名功能模块具体实现基于WebMagic实现的功能基类
 *
 * @author RichardTang
 * @date 2020/4/10
 */
public abstract class AbstractDomainModuleProcessor extends AbstractDomainModuleBase implements PageProcessor {

    protected String api;

    protected String regex;

    protected Site site = Site.me();

    protected HashSet<String> result;

    protected AbstractDomainModuleProcessor(Integer type) {
        this(type, "");
    }

    protected AbstractDomainModuleProcessor(Integer type, String api) {
        super(type);
        this.api = api;
        this.result = new HashSet<String>();
    }

    /**
     * 页面具体的处理函数
     *
     * @param page 页面
     */
    @Override
    public abstract void process(Page page);

    /**
     * 根据currentTask中的target来查找Html中符合规则的域名
     * @param html 页面html标签
     */
    protected List<String> findDomainByTarget(Html html) {
        if (StrUtil.isEmpty(this.regex)) {
            String[] split1 = currentTask.getTarget().split("\\.");
            this.regex = String.format("[a-z0-9A-Z.-]*\\.%s\\.%s", split1[0],split1[1]);
        }
        return html.regex(regex).all();
    }

    protected void findDomainToResult(Html html) {
        result.addAll(findDomainByTarget(html));
    }

    @Override
    public Site getSite() {
        String ip = NetUtils.getRandomIp();
        return site
                .setTimeOut(3000)
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
