package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.JmMtstd;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmMtstdMfEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JmMtstdMfDao extends BaseDao<JmMtstdMfEntity> {

    List<JmMtstd> getJmMtstd(ResultType dto);

}
