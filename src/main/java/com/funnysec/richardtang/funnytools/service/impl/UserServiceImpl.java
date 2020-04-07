package com.funnysec.richardtang.funnytools.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funnysec.richardtang.funnytools.entity.User;
import com.funnysec.richardtang.funnytools.mapper.UserMapper;
import com.funnysec.richardtang.funnytools.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * 用户业务
 *
 * @author RichardTang
 * @date 2020年3月15日21:42:40
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
