package com.funnysec.richardtang.funnytools.task.pipeline;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
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
 * 操作Domain表的Pipline
 *
 * @author RichardTang
 * @date 2020/4/9
 */
public class DomainPipeline implements Pipeline {

    /**
     * PiplineKey和Processor对应
     */
    public String key;

    /**
     * Spring是线程安全的所以Autowired无法注入
     * 所以采用参数构造的方式进行传递
     */
    private IDomainService service;

    public DomainPipeline(String key) {
        this.key = key;
        this.service = SpringUtil.getBean(IDomainService.class);
    }


    /**
     * 数据具体如何存储到数据库中的Domain表中
     * 已经实现具体的去重步骤
     *
     * @param resultItems 结果集合
     * @param task        任务对象
     */
    @Override
    public void process(ResultItems resultItems, Task task) {
        HashSet<String> result = resultItems.get(key);
        // 判断result是否为空并且对result进行去重后再次判断是否为空
        if (CollectionUtil.isEmpty(result) || CollectionUtil.isEmpty(clearRepeat(result))) {
            return;
        }

        List<Domain> domains = selectIp(result);
        if (CollectionUtil.isNotEmpty(domains)) {
            service.saveBatch(domains);
        }
    }

    /**
     * 将去重后的ip挨个包装成DomainEntity
     *
     * @param result ip集合
     * @return DomainEntity集合
     */
    private List<Domain> selectIp(HashSet<String> result) {
        // 将不为重复的数据查询A记录
        List<Domain> domains = new ArrayList<Domain>();
        result.forEach(i -> {
            String ip = NetUtils.getIp(i);
            if (StrUtil.isNotEmpty(ip)) {
                domains.add(new Domain(ip, i));
            }
        });
        return domains;
    }

    /**
     * 根据数据库中已有的数据进行去重
     *
     * @param result 需要被赛选的数据
     * @return HashSet去重后的数据
     */
    private HashSet<String> clearRepeat(HashSet<String> result) {
        // 将result中的数据做in条件到数据库中查询一遍,
        List<String> domain = service.query()
                .in("domain", result)
                .list()
                .stream()
                .map(Domain::getDomain)
                .collect(Collectors.toList());

        // 查询出来的代表数据库中已经存在,从result中删除
        result.removeAll(domain);
        return result;
    }
}
