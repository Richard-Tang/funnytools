package com.funnysec.richardtang.funnytools.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 业务接口基类
 *
 * @param <T> entity
 */
public interface IBaseService<T> extends IService<T> {

    /**
     * 指定字段名为判断更新或者添加的依据
     *
     * @param entity     需要处理的实体数据
     * @param columnName 数据库字段名
     * @return boolean 是否操作成功
     */
    boolean saveOrUpdateByColumnName(T entity, String... columnName);

    /**
     * 指定字段名为判断更新或者添加的依据
     *
     * @param entityList 需要处理的实体数据
     * @param columnName 数据库字段名
     * @return boolean 是否操作成功
     */
    boolean saveOrUpdateBatchByColumnName(List<T> entityList, String... columnName);
}
