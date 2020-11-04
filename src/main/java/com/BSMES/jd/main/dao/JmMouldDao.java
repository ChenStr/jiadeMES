package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.JmMouldDTO;
import com.BSMES.jd.main.entity.JmMouldEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JmMouldDao extends BaseDao<JmMouldEntity> {

    /**
     * 修改模具
     * @param jmMouldDTO
     */
    public void editMould(JmMouldDTO jmMouldDTO);

}
