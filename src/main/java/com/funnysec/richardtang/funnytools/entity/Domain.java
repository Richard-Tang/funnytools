package com.funnysec.richardtang.funnytools.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 域名
 *
 * @author RichardTang
 * @date 2020年3月15日20:35:04
 */
@Data
@TableName("domain")
public class Domain {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @TableField
    private String ip;

    @TableField
    private String domain;

    @JsonFormat(pattern = "yyyy年MM月dd日 ss:hh:mm")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date createTime;

    public Domain() {
        this.createTime = new Date();
    }

    public Domain(String ip, String domain) {
        this.ip = ip;
        this.domain = domain;
        this.createTime = new Date();
    }
}
