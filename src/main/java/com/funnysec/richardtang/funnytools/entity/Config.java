package com.funnysec.richardtang.funnytools.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 功能配置
 *
 * @author RichardTang
 * @date 2020/3/17
 */
@Data
@TableName("config")
@NoArgsConstructor
@RequiredArgsConstructor
public class Config {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @NonNull
    @TableField
    private Integer type;

    @NonNull
    @TableField
    private String ini;

}
