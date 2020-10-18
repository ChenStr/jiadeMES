package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.JmMtdd2TfDTO;
import com.BSMES.jd.main.entity.JmMtdd2TfEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JmMtdd2TfDao extends BaseDao<JmMtdd2TfEntity> {

    void saveJmMtdd2TfS(List<JmMtdd2TfDTO> jmMtdd2TfDTOS);

    void updateJmMtdd2Tf(JmMtdd2TfDTO jmMtdd2TfDTOS);

}
