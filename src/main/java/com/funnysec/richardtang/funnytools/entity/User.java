package com.funnysec.richardtang.funnytools.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户
 *
 * @author RichardTang
 * @date 2020年3月15日20:39:38
 */
@Data
@TableName("user")
public class User {
	
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	@TableField
	private String avatar;

	@TableField
	private String email;

	@TableField
	private String phone;

	@TableField
	private String username;
	
	@TableField
	private String password;
	
}
