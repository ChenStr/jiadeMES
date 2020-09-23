package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.entity.JmWorkerEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 人员表的Dao层
 */
@Mapper
public interface JmWorkerDao extends BaseDao<JmWorkerEntity> {

}
