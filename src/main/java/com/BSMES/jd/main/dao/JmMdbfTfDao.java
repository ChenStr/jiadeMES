package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.JmMdbfTfDTO;
import com.BSMES.jd.main.entity.JmMdbfTfEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JmMdbfTfDao extends BaseDao<JmMdbfTfEntity> {

    /**
     * 批量添加
     */
    public void saveMdbf(List<JmMdbfTfDTO> dtos);

    /**
     * 更新
     */
    public void editMdbf(JmMdbfTfDTO dto);

}
