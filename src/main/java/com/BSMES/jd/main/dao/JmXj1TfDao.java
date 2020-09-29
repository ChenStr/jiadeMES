package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.JmXj1TfDTO;
import com.BSMES.jd.main.entity.JmXj1TfEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JmXj1TfDao extends BaseDao<JmXj1TfEntity> {

    void updateJmXj1Tf(JmXj1TfDTO dto);

}
