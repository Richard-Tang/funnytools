package com.funnysec.richardtang.funnytools.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funnysec.richardtang.funnytools.constant.State;
import com.funnysec.richardtang.funnytools.entity.Task;
import com.funnysec.richardtang.funnytools.mapper.TaskMapper;
import com.funnysec.richardtang.funnytools.service.ITaskService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 任务业务
 *
 * @author RichardTang
 * @date 2020年3月15日21:41:37
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

    @Override
    public boolean saveBatchTask(List<String> target, Integer type) {
        List<@NonNull String> existTarget = query()
                .eq("type", type)
                .ne("state", State.TASK_COMPLETE)
                .in("target", target)
                .list()
                .stream()
                .map(Task::getTarget)
                .collect(Collectors.toList());
        target.removeAll(existTarget);
        List<Task> task = new ArrayList<Task>();
        target.forEach(i -> {
            task.add(new Task(i, type));
        });
        return saveBatch(task);
    }
}
