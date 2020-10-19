package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.BomPlus;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmBomMfEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JmBomMfDao extends BaseDao<JmBomMfEntity> {

    public List<BomPlus> getJmBomMfDao(ResultType dto);

}
