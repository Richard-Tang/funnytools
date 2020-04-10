package com.funnysec.richardtang.funnytools.task;

import com.funnysec.richardtang.funnytools.constant.Pipeline;
import com.funnysec.richardtang.funnytools.constant.State;
import com.funnysec.richardtang.funnytools.constant.Type;
import com.funnysec.richardtang.funnytools.task.base.AbstractDomainTask;
import com.funnysec.richardtang.funnytools.task.ini.DomainBaiduSearchIni;
import com.funnysec.richardtang.funnytools.task.pipeline.DomainPipeline;
import com.funnysec.richardtang.funnytools.task.processer.BaiduSearchProcesser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

/**
 * @author RichardTang
 * @date 2020/3/21
 */
@Component
public class DomainBaiduSearchTask extends AbstractDomainTask {

    @Autowired
    private DomainBaiduSearchIni domainBaiduSearchIni;

    protected DomainBaiduSearchTask() {
        super(Type.TASK_DOMAIN_BAIDU_SEARCH);
    }

    @Override
    public boolean isOk(String target) {
        return true;
    }

    @Override
    public void start() {
        updateTaskState(State.TASK_ING);
        String target = task.getTarget();
        Spider.create(new BaiduSearchProcesser(target, Pipeline.BAIDU_SEARCH_PIPELINE_KEY, domainBaiduSearchIni.getBaiduSearchApi()))
                .addUrl(String.format(domainBaiduSearchIni.getBaiduSearchApi(), "0", target))
                .addPipeline(new DomainPipeline(Pipeline.BAIDU_SEARCH_PIPELINE_KEY))
                .run();
    }
}
