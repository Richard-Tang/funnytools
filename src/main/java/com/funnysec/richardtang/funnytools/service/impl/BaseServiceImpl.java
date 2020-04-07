package com.funnysec.richardtang.funnytools.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funnysec.richardtang.funnytools.service.IBaseService;
import com.funnysec.richardtang.funnytools.utils.FieldUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * IBaseService实现类
 *
 * @param <M> mapper
 * @param <T> entity
 * @author RichardTang
 * @date 2020年3月15日21:36:08
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IBaseService<T> {

    @Override
    public boolean saveOrUpdateByColumnName(T entity, String... columnName) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        Arrays.asList(columnName).forEach(column -> {
            try {
                String methodName = String.format("get%s", StrUtil.upperFirst(FieldUtils.lineToHump(column)));
                Object value = currentModelClass().getMethod(methodName).invoke(entity);
                queryWrapper.eq(column, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return saveOrUpdate(entity, queryWrapper);
    }

    @Override
    public boolean saveOrUpdateBatchByColumnName(List<T> entityList, String... columnName) {
        AtomicBoolean flag = new AtomicBoolean(false);
        entityList.forEach(item -> {
            flag.set(saveOrUpdateByColumnName(item, columnName));
        });
        return flag.get();
    }
}
