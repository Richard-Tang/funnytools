package com.funnysec.richardtang.funnytools.service.impl;

import com.funnysec.richardtang.funnytools.entity.Domain;
import com.funnysec.richardtang.funnytools.mapper.DomainMapper;
import com.funnysec.richardtang.funnytools.service.IDomainService;
import com.funnysec.richardtang.funnytools.utils.NetUtils;
import org.springframework.stereotype.Service;

/**
 * 域名业务
 *
 * @author RichardTang
 * @date 2020年3月15日21:38:18
 */
@Service
public class DomainServiceImpl extends BaseServiceImpl<DomainMapper, Domain> implements IDomainService {

    @Override
    public boolean insert(String domain) {
        String ip = NetUtils.getIp(domain);
        return save(new Domain(ip, domain));
    }
}
