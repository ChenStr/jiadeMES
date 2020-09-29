package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.JmMtstdTfDTO;
import com.BSMES.jd.main.entity.JmMtstdTfEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JmMtstdTfDao extends BaseDao<JmMtstdTfEntity> {

    void UpdateJmMtstdTf(JmMtstdTfDTO jmMtstdTfDTO);

}
