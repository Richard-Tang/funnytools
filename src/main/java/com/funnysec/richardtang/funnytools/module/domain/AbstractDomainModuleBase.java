package com.funnysec.richardtang.funnytools.module.domain;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.funnysec.richardtang.funnytools.constant.State;
import com.funnysec.richardtang.funnytools.entity.Task;
import com.funnysec.richardtang.funnytools.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 域名模块功能基类
 *
 * @author RichardTang
 * @date 2020/4/10
 */
public abstract class AbstractDomainModuleBase {

    protected Integer type;

    protected Task currentTask;

    protected Boolean lock = true;

    @Autowired
    protected ITaskService taskService;

    protected AbstractDomainModuleBase(Integer type) {
        this.type = type;
    }

    /**
     * 父类完成获取任务,关闭任务的工作
     * 任务具体的操作实现请覆盖 start(String target);
     */
    @Scheduled(fixedDelay = 10000)
    protected void start() {
        System.out.println("=====================" + getClass().getSimpleName() + ":" + lock + "===============================");
        // 判断锁是否释放,未释放将不从数据库获取新任务
        if (!lock) {
            return;
        }

        // 上锁避免其他线程进来,这里不需要线程同步,因为 @Scheduled 每10秒才执行一次
        lock = false;

        // 为空代表数据库没有当前类适合的任务要处理
        getTask();
        if (currentTask == null) {
            lock = true;
            return;
        }

        // 任务状态更新失败程序结束
        if (!updateTaskState(State.TASK_ING)) {
            closeTask();
            return;
        }

        try {
            start(currentTask.getTarget());
        } finally {
            closeTask();
        }
    }

    /**
     * 父类会对任务自动获取和关闭,子类只需要实现如何开始任务即可
     *
     * @param target 目标域名
     */
    protected abstract void start(String target);

    /**
     * 从数据库中获取当前任务
     */
    protected void getTask() {
        currentTask = taskService.getOne(new QueryWrapper<Task>()
                .eq("state", State.TASK_WAIT)
                .eq("type", type)
                .orderByAsc("create_time"), false);
    }

    /**
     * 关闭任务
     */
    protected void closeTask() {
        updateTaskState(State.TASK_COMPLETE);
        lock = true;
        currentTask = null;
    }

    /**
     * 更新任务状态
     *
     * @param state 任务状态
     */
    protected boolean updateTaskState(Integer state) {
        currentTask.setState(state);
        return taskService.updateById(currentTask);
    }

}
