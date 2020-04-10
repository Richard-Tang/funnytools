package com.funnysec.richardtang.funnytools.task;

import com.funnysec.richardtang.funnytools.constant.Pipeline;
import com.funnysec.richardtang.funnytools.constant.State;
import com.funnysec.richardtang.funnytools.constant.Type;
import com.funnysec.richardtang.funnytools.task.base.AbstractDomainTask;
import com.funnysec.richardtang.funnytools.task.pipeline.DomainPipeline;
import com.funnysec.richardtang.funnytools.task.processer.DomainPageSearchProcesser;
import com.funnysec.richardtang.funnytools.utils.NetUtils;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

/**
 * 页面查找域名,通过使用爬虫技术在页面和页面的Js文件中查找隐藏的域名
 *
 * @author RichardTang
 * @date 2020/3/21
 */
@Component
public class DomainPageSearchTask extends AbstractDomainTask {

    protected DomainPageSearchTask() {
        super(Type.TASK_DOMAIN_PAGE_SEARCH);
    }

    @Override
    public boolean isOk(String target) {
        return true;
    }

    @Override
    public void start() {
        updateTaskState(State.TASK_ING);
        String target = task.getTarget();
        String url = NetUtils.getHttpProtocol(target) + target;
        // 不需要判断目标页面是否存在,WebMagic已解决该问题
        Spider.create(new DomainPageSearchProcesser(target, Pipeline.DOMAIN_PAGE_PIPELINE_KEY))
                .addPipeline(new DomainPipeline(Pipeline.DOMAIN_PAGE_PIPELINE_KEY))
                .addUrl(url).run();
    }
}
