package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.entity.JmDevEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 设备表(加工资源)的Dao层
 */
@Mapper
public interface JmDevDao extends BaseDao<JmDevEntity> {

}
