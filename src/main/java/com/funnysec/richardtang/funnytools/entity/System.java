package com.funnysec.richardtang.funnytools.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("system")
public class System {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 系统菜单
     */
    @TableField
    private String menu;

    /**
     * 系统标题
     */
    @TableField
    private String title;

    /**
     * 系统版本
     */
    @TableField
    private String version;

    /**
     * 系统特色
     */
    @TableField
    private String characteristics;
}
