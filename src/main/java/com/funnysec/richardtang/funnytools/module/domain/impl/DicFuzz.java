package com.funnysec.richardtang.funnytools.module.domain.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.StrUtil;
import com.funnysec.richardtang.funnytools.constant.*;
import com.funnysec.richardtang.funnytools.entity.Domain;
import com.funnysec.richardtang.funnytools.entity.Task;
import com.funnysec.richardtang.funnytools.module.domain.AbstractDomainModuleBase;
import com.funnysec.richardtang.funnytools.module.domain.ini.DomainModuleDicFuzzIni;
import com.funnysec.richardtang.funnytools.service.IDomainService;
import com.funnysec.richardtang.funnytools.utils.NetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 字典枚举域名模块
 *
 * @author RichardTang
 * @date 2020/4/10
 */
@Component
public class DicFuzz extends AbstractDomainModuleBase {

    @Value("${project.file-base-path}")
    private String fileBasePath;

    @Autowired
    private IDomainService domainService;

    @Autowired
    private DomainModuleDicFuzzIni ini;

    private DicFuzz() {
        super(Type.TASK_DOMAIN_DIC_FUZZ);
    }

    /**
     * 初始化操作
     */
    @Override
    public void start(String target) {
        // 判断域名是否为泛域名解析
        if (NetUtils.isPanDomain(target)) {
            closeTask();
            return;
        }

        try {
            // 创建字典
            List<String> dicLines = createDicLines();
            if (CollUtil.isNotEmpty(dicLines)) {
                // 创建任务列表
                List<DicFuzz.DomainScanTaskExecutor> taskList = createTaskList(dicLines);
                // 创建线程池
                ThreadPoolExecutor threadPoolExecutor = createThreadPoolExecutor();
                threadPoolExecutor.invokeAll(taskList);
                threadPoolExecutor.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得一个线程池
     *
     * @return ThreadPoolExecutor
     */
    private ThreadPoolExecutor createThreadPoolExecutor() {
        // 注意threadSize不允许小于corePoolSize
        return new ThreadPoolExecutor(ini.getThreadSize(),
                ini.getThreadSize(),
                1000,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    /**
     * 获取字典数据
     *
     * @return List<String> 字典集合
     */
    private List<String> createDicLines() {
        String filePath = fileBasePath + ini.getDicName();
        if (FileUtil.exist(filePath)) {
            FileReader fileReader = FileReader.create(FileUtil.file(filePath));
            return fileReader.readLines();
        }
        return null;
    }

    /**
     * 创建任务列表,分配每个任务线程所需要使用的字典范围
     *
     * @param dic 字典
     * @return List<DomainScanTaskExecutor> 任务列表
     */
    private List<DicFuzz.DomainScanTaskExecutor> createTaskList(List<String> dic) {
        // 线程总大小
        int threadSize = ini.getThreadSize();
        // 每个线程所需要处理的字典行数
        int eachThreadDicNum = dic.size() / threadSize;

        List<DicFuzz.DomainScanTaskExecutor> tasks = new ArrayList<DicFuzz.DomainScanTaskExecutor>();

        for (int i = 0; i < threadSize; i++) {

            // 计算线程i在字典中的开始行和结束行
            int start = eachThreadDicNum * i;
            int end = start + eachThreadDicNum;

            // 最后一个线程需要额外处理
            if (i == (threadSize - 1)) {
                end += (dic.size() - end);
            }

            tasks.add(new DicFuzz.DomainScanTaskExecutor(dic.subList(start, end)));
        }
        return tasks;
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
                String domain = String.format("%s.%s", line, currentTask.getTarget());
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
