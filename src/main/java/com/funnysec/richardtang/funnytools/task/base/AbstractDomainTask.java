package com.funnysec.richardtang.funnytools.task.base;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.funnysec.richardtang.funnytools.constant.TaskState;
import com.funnysec.richardtang.funnytools.entity.Task;
import com.funnysec.richardtang.funnytools.service.IDomainService;
import com.funnysec.richardtang.funnytools.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 域名类功能任务基类
 *
 * @author RichardTang
 * @date 2020/3/21
 */
public abstract class AbstractDomainTask implements IBaseTask {

    /**
     * 任务类型
     */
    private int taskType;

    /**
     * 当前处理类要执行的任务对象
     */
    protected Task task;

    /**
     * 锁机制,为了让一个时间段内一个Task只处理一个目标
     */
    protected static volatile boolean lock = true;

    @Autowired
    protected ITaskService taskService;

    @Autowired
    protected IDomainService domainService;

    protected AbstractDomainTask(int taskType) {
        this.taskType = taskType;
    }

    @Override
    @Scheduled(fixedRate = 10000)
    public void process() {
        if (lock) {
            lock = false;

            // 查看数据库中是否有任务
            if (ObjectUtil.isNull(getTask())) {
                lock = true;
                return;
            }

            // 符合进行任务的条件
            if (isOk(getTask().getTarget())) {
                try {
                    updateTaskState(TaskState.ING);
                    // 开始任务
                    start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            closeTask();
        }
    }

    /**
     * 关闭任务
     */
    @Override
    public void closeTask() {
        updateTaskState(TaskState.COMPLETE);
        task = null;
        lock = true;
    }

    /**
     * 更新任务状态
     *
     * @param state 任务状态
     */
    @Override
    public void updateTaskState(Integer state) {
        task.setState(state);
        taskService.updateById(task);
    }

    /**
     * 获取符合当前任务类执行的任务
     *
     * @return Task 任务对象
     */
    @Override
    public Task getTask() {
        if (ObjectUtil.isNull(task)) {
            task = taskService.getOne(new QueryWrapper<Task>()
                    .eq("state", TaskState.WAIT)
                    .eq("type", taskType)
                    .orderByAsc("create_time"), false);
        }
        return task;
    }
}
