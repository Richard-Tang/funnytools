package com.funnysec.richardtang.funnytools.task.pipeline;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.funnysec.richardtang.funnytools.entity.Domain;
import com.funnysec.richardtang.funnytools.service.IDomainService;
import com.funnysec.richardtang.funnytools.utils.NetUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 将 DomainPageSearchProcesser 处理好的结果进行赛选存入数据库中
 *
 * @author RichardTang
 * @date 2020/3/21
 */
public class DomainPageSearchPipeline implements Pipeline {

    /**
     * spring线程安全无法注入,所以采用传递的方式
     */
    private IDomainService domainService;

    public DomainPageSearchPipeline(IDomainService domainService) {
        this.domainService = domainService;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        HashSet<String> result = resultItems.get("DomainPageSearchProcesser");
        if (CollUtil.isNotEmpty(result)) {
            List<Domain> domains = new ArrayList<Domain>();
            List<String> domain = domainService.query()
                    .in("domain", result)
                    .list()
                    .stream()
                    .map(Domain::getDomain)
                    .collect(Collectors.toList());

            // 去除数据库中已存在的数据
            result.removeAll(domain);

            // 将不为重复的数据查询A记录 存在IP的将存入数据库中
            result.forEach(i -> {
                String ip = NetUtils.getIp(i);
                if (StrUtil.isNotEmpty(ip)) {
                    domains.add(new Domain(ip, i));
                }
            });
            domainService.saveBatch(domains);
        }
    }
}
