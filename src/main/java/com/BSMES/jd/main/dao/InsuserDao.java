package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.entity.InsuserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表的Dao层
 */
@Mapper
public interface InsuserDao extends BaseDao<InsuserEntity> {

}
