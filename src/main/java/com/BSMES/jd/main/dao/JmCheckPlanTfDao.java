package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.JmBomTfDTO;
import com.BSMES.jd.main.dto.JmCheckPlanTfDTO;
import com.BSMES.jd.main.entity.JmCheckPlanTfEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JmCheckPlanTfDao extends BaseDao<JmCheckPlanTfEntity> {

    /**
     * 批量新增Bom子表
     */
    public void savePlanTfS(List<JmCheckPlanTfDTO> jmCheckPlanTfDTOS);


    public void updatePlanTf(JmCheckPlanTfDTO jmCheckPlanTfDTO);

}
