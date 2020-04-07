package com.funnysec.richardtang.funnytools.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.funnysec.richardtang.funnytools.constant.TaskState;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * 任务
 *
 * @author RichardTang
 * @date 2020年3月15日20:38:55
 */
@Data
@TableName("task")
@NoArgsConstructor
@RequiredArgsConstructor
public class Task {

    @TableId(type = IdType.AUTO)
    private Long id;

    @NonNull
    @TableField
    private String target;

    @NonNull
    @TableField
    private Integer state;

    @NonNull
    @TableField
    private Integer type;

    @TableField
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime = new Date();

    public Task(String target, Integer type) {
        this.target = target;
        this.type = type;
        this.state = TaskState.WAIT;
    }
}
