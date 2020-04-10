package com.funnysec.richardtang.funnytools.task;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.funnysec.richardtang.funnytools.constant.Character;
import com.funnysec.richardtang.funnytools.constant.State;
import com.funnysec.richardtang.funnytools.constant.Type;
import com.funnysec.richardtang.funnytools.entity.Domain;
import com.funnysec.richardtang.funnytools.task.base.AbstractDomainTask;
import com.funnysec.richardtang.funnytools.task.ini.DomainCertSearchIni;
import com.funnysec.richardtang.funnytools.utils.NetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 通过证书进行搜索
 *
 * @author RichardTang
 * @date 2020/3/21
 */
@Component
public class DomainCertSearchBaseTask extends AbstractDomainTask {

    /**
     * 用来匹配字符串中符合要获取格式的数据的正则表达式
     */
    private String regex;

    @Autowired
    private DomainCertSearchIni domainCertSearchIni;

    protected DomainCertSearchBaseTask() {
        super(Type.TASK_DOMAIN_CERT_SEARCH);
    }

    @Override
    public boolean isOk(String target) {
        return true;
    }

    @Override
    public void start() {
        updateTaskState(State.TASK_ING);
        List<String> split = StrUtil.splitTrim(task.getTarget(), Character.POINTER);
        this.regex = String.format("[a-z0-9A-Z.-]*\\.%s\\.%s", CollUtil.get(split, split.size() - 2), CollUtil.getLast(split));
        crtShApi();
        ctSearchApi();
    }

    /**
     * crt-sh-api
     */
    private void crtShApi() {
        String html = HttpUtil.get(domainCertSearchIni.getCrtShApi() + task.getTarget())
                .replace("*.", Character.BLANK);
        parseHtmlSaveToDb(html);
    }

    /**
     * ct-search-api
     */
    private void ctSearchApi() {
        String html = HttpUtil.get(domainCertSearchIni.getCrtShApi() + task.getTarget())
                .replace("cn\\u003d", Character.BLANK)
                .replace("*.", Character.BLANK);
        parseHtmlSaveToDb(html);
    }

    /**
     * 将 parseTargetDomainByHtml() resultToDb() 合并成一个函数
     *
     * @param html html字符
     */
    private void parseHtmlSaveToDb(String html) {
        resultToDb(parseTargetDomainByHtml(html));
    }

    /**
     * 从Html字符中解析出符合target的域名
     *
     * @param html html字符
     * @return HashSet 从html中解析出来的域名
     */
    private HashSet<String> parseTargetDomainByHtml(String html) {
        return ReUtil.findAll(regex, html, 0, new HashSet<String>());
    }

    /**
     * 将数据存入数据库中, 不会存储重复的数据,会对数据进行赛选操作
     *
     * @param domain 数据集合
     */
    private void resultToDb(HashSet<String> domain) {
        List<String> existDomain = domainService.query()
                .in("domain", domain)
                .list()
                .stream()
                .map(Domain::getDomain)
                .collect(Collectors.toList());
        domain.removeAll(existDomain);
        List<Domain> result = new ArrayList<Domain>();
        domain.forEach(i -> {
            String ip = NetUtils.getIp(i);
            if (StrUtil.isNotEmpty(ip)) {
                result.add(new Domain(ip, i));
            }
        });
        domainService.saveBatch(result);
    }
}
