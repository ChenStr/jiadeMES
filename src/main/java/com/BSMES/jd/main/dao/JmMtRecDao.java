package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.JmMtRec;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmMtRecEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JmMtRecDao extends BaseDao<JmMtRecEntity> {

}
