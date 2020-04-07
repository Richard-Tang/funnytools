package com.funnysec.richardtang.funnytools.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funnysec.richardtang.funnytools.entity.System;
import com.funnysec.richardtang.funnytools.entity.User;
import com.funnysec.richardtang.funnytools.mapper.SystemMapper;
import com.funnysec.richardtang.funnytools.mapper.UserMapper;
import com.funnysec.richardtang.funnytools.service.ISystemService;
import com.funnysec.richardtang.funnytools.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * 系统配置业务
 *
 * @author RichardTang
 * @date 2020/4/7
 */
@Service
public class SystemServiceImpl extends ServiceImpl<SystemMapper, System> implements ISystemService {

}
