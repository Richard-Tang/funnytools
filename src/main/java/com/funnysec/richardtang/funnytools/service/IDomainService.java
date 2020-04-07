package com.funnysec.richardtang.funnytools.service;

import com.funnysec.richardtang.funnytools.entity.Domain;

/**
 * 域名业务接口
 *
 * @author RichardTang
 * @date 2020/3/17
 */
public interface IDomainService extends IBaseService<Domain> {

    /**
     * 添加域名,同时将解析出来的多个IP格式化
     *
     * @param domain 域名
     * @return boolean 是否添加成功
     */
    boolean insert(String domain);

}
