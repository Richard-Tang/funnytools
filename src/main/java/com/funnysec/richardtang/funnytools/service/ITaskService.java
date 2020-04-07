package com.funnysec.richardtang.funnytools.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.funnysec.richardtang.funnytools.entity.Task;

import java.util.List;

/**
 * 任务业务接口
 *
 * @author RichardTang
 * @date 2020年3月15日21:44:05
 */
public interface ITaskService extends IService<Task> {

    /**
     * 批量添加任务,不会添加已经存在的任务
     *
     * @param target 目标集合
     * @param type 任务类型
     * @return boolean 是否操作成功
     */
    boolean saveBatchTask(List<String> target, Integer type);
}
