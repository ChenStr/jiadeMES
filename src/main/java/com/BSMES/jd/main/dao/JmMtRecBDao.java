package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.JmMtRecBDTO;
import com.BSMES.jd.main.entity.JmMtRecBEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JmMtRecBDao extends BaseDao<JmMtRecBEntity> {

    /**
     * 批量的新增
     * @param dtos
     */
    public void saveMtRecBs(List<JmMtRecBDTO> dtos);

    /**
     * 修改
     * @param dto
     */
    public void editMtRecB(JmMtRecBDTO dto);

}
