package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.JmMdbkTfDTO;
import com.BSMES.jd.main.entity.JmMdbkTfEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JmMdbkTfDao extends BaseDao<JmMdbkTfEntity> {

    /**
     * 批量新增
     * @param jmMdbkTfDTOS
     */
    public void saveJmMdbkTfs(List<JmMdbkTfDTO> jmMdbkTfDTOS);

    public void editJmMdbkTf(JmMdbkTfDTO jmMdbkTfDTO);
}
