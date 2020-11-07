package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.JmGzstdTfDTO;
import com.BSMES.jd.main.dto.JmJobRecBDTO;
import com.BSMES.jd.main.entity.JmGzstdTfEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JmGzstdTfDao extends BaseDao<JmGzstdTfEntity> {

    /**
     * 批量添加
     */
    public void insertGzstd(List<JmGzstdTfDTO> jmGzstdTfDTOS);

    /**
     * 修改
     */
    public void editGzstd(JmGzstdTfDTO jmGzstdTfDTO);

}
