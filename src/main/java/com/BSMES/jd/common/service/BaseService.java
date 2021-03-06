package com.BSMES.jd.common.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * 浮桥Service层 (方法基本围绕DTO)
 *
 * @author DNYY
 *
 * T为Entity类型
 *
 * D为DTO类型
 */
public interface BaseService<T,D> extends DeepService<T> {

    public void insert(D dto);

    public D selectById (String id);

    public List<D> selectByIds(List<String> ids);

    public void edit(D dto);

    public Boolean deleteById(String id);

    public Boolean deleteByIds(List<String> ids);

    public List<D> select(Map<String,Object> data);

    public List<D> select(QueryWrapper<T> wrapper);

    public IPage<T> selectPage(Object page,Object pageSize, QueryWrapper<T> queryWrapper);

    public D selectOne(QueryWrapper<T> wrapper);

}
