package com.funnysec.richardtang.funnytools.task.base;

import com.funnysec.richardtang.funnytools.entity.Task;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 任务接口
 *
 * @author RichardTang
 * @date 2020/3/21
 */
public interface IBaseTask {

    /**
     * 该目标是否符合进行任务的条件
     *
     * @param target 目标(域名)
     * @return boolean true:对该目录正常执行该任务 false:不对该目标进行任务
     */
    abstract boolean isOk(String target);

    /**
     * 任务具体的处理逻辑,编写逻辑时可能会遇到一些异常情况,直接抛出process()中会进行捕获处理
     *
     * @throws Exception
     */
    abstract void start() throws Exception;

    /**
     * 获取符合当前任务类执行的任务
     *
     * @return Task 任务对象
     */
    Task getTask();

    /**
     * 关闭任务
     */
    void closeTask();

    /**
     * 更新任务状态
     *
     * @param state 任务状态
     */
    void updateTaskState(Integer state);

    /**
     * SpringScheduled 的入口函数
     */
    void process();
}
