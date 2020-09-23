package com.BSMES.jd.common.service.impl;


import com.BSMES.jd.common.service.DeepService;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 最底层的Service实现类(方法围绕操作Entity)
 *
 * M 为 Dao 层
 *
 * T 为 Entity
 *
 * @author DNYY
 */
public abstract class DeepServiceImpl<M extends BaseMapper<T>,T> extends ServiceImpl<M,T> implements DeepService<T> {



}
