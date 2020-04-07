package com.funnysec.richardtang.funnytools.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funnysec.richardtang.funnytools.entity.Config;
import com.funnysec.richardtang.funnytools.mapper.ConfigMapper;
import com.funnysec.richardtang.funnytools.service.IConfigService;
import org.springframework.stereotype.Service;

/**
 * 功能配置业务
 *
 * @author RichardTang
 * @date 2020年3月17日18:08:40
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {

}
