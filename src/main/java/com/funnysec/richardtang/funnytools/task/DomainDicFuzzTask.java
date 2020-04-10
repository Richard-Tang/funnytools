package com.funnysec.richardtang.funnytools.task;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.StrUtil;
import com.funnysec.richardtang.funnytools.constant.State;
import com.funnysec.richardtang.funnytools.constant.Type;
import com.funnysec.richardtang.funnytools.entity.Domain;
import com.funnysec.richardtang.funnytools.task.base.AbstractDomainTask;
import com.funnysec.richardtang.funnytools.task.ini.DomainDicFuzzIni;
import com.funnysec.richardtang.funnytools.utils.NetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 域名扫描任务
 *
 * @author RichardTang
 * @date 2020年3月16日19:22:17
 */
@Component
public class DomainDicFuzzTask extends AbstractDomainTask {

    @Autowired
    private DomainDicFuzzIni domainDicFuzzIni;

    @Value("${project.file-base-path}")
    private String fileBasePath;

    protected DomainDicFuzzTask() {
        super(Type.TASK_DOMAIN_DIC_FUZZ);
    }

    @Override
    public boolean isOk(String target) {
        return !NetUtils.isPanDomain(task.getTarget());
    }

    /**
     * 初始化操作,创建线程池,分配每个线程所使用的字典数据
     */
    @Override
    public void start() throws InterruptedException {
        updateTaskState(State.TASK_ING);
        // 创建字典
        List<String> dicLines = createDicLines();
        if (CollUtil.isNotEmpty(dicLines)) {
            // 创建任务列表
            List<DomainScanTaskExecutor> taskList = createTaskList(dicLines);
            // 创建线程池
            ThreadPoolExecutor threadPoolExecutor = createThreadPoolExecutor();
            threadPoolExecutor.invokeAll(taskList);
            threadPoolExecutor.shutdown();
        }
    }

    /**
     * 创建任务列表,分配每个任务线程所需要使用的字典范围
     *
     * @param dic 字典
     * @return List<DomainScanTaskExecutor> 任务列表
     */
    private List<DomainScanTaskExecutor> createTaskList(List<String> dic) {
        // 线程总大小
        int threadSize = domainDicFuzzIni.getThreadSize();
        // 每个线程所需要处理的字典行数
        int eachThreadDicNum = dic.size() / threadSize;

        List<DomainScanTaskExecutor> tasks = new ArrayList<DomainScanTaskExecutor>();

        for (int i = 0; i < threadSize; i++) {

            // 计算线程i在字典中的开始行和结束行
            int start = eachThreadDicNum * i;
            int end = start + eachThreadDicNum;

            // 最后一个线程需要额外处理
            if (i == (threadSize - 1)) {
                end += (dic.size() - end);
            }

            tasks.add(new DomainScanTaskExecutor(dic.subList(start, end)));
        }
        return tasks;
    }

    /**
     * 获取字典数据
     *
     * @return List<String> 字典集合
     */
    private List<String> createDicLines() {
        String filePath = fileBasePath + domainDicFuzzIni.getDicName();
        if (FileUtil.exist(filePath)) {
            FileReader fileReader = FileReader.create(FileUtil.file(filePath));
            return fileReader.readLines();
        }
        return null;
    }

    /**
     * 获得一个线程池
     *
     * @return ThreadPoolExecutor
     */
    private ThreadPoolExecutor createThreadPoolExecutor() {
        // 注意threadSize不允许小于corePoolSize
        return new ThreadPoolExecutor(domainDicFuzzIni.getThreadSize(),
                domainDicFuzzIni.getThreadSize(),
                1000,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    class DomainScanTaskExecutor implements Callable<Boolean> {

        /**
         * 字典数据
         */
        private List<String> dic;

        DomainScanTaskExecutor(List<String> dic) {
            this.dic = dic;
        }

        /**
         * 每个单独的线程所执行的逻辑
         */
        @Override
        public Boolean call() {
            List<Domain> domainList = new ArrayList<Domain>();
            for (String line : dic) {
                // 将字典中的数据和域名拼接 xxx.baidu.com
                String domain = String.format("%s.%s", line, task.getTarget());
                // 查询是否有解析
                String ip = NetUtils.getIp(domain);
                // 为空进行下一个域名判断
                if (StrUtil.isEmpty(ip)) {
                    continue;
                }
                domainList.add(new Domain(ip, domain));
            }
            return domainService.saveOrUpdateBatchByColumnName(domainList, "domain", "ip");
        }
    }
}