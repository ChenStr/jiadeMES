package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.JmBomTfDTO;
import com.BSMES.jd.main.entity.JmBomTfEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JmBomTfDao extends BaseDao<JmBomTfEntity> {

    /**
     * 批量新增Bom子表
     */
    public void saveBoms(List<JmBomTfDTO> jmBomTfDTOS);


    public void updateBoms();

}
