package com.BSMES.jd.main.dao;


import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.JmChkstdTfDTO;
import com.BSMES.jd.main.entity.JmChkstdMfEntity;
import com.BSMES.jd.main.entity.JmChkstdTfEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JmChkstdTfDao extends BaseDao<JmChkstdTfEntity> {

    /**
     * 批量插入
     */
    void insertJmChkstdTfs(List<JmChkstdTfDTO> jmChkstdTfDTOS);

    /**
     * 更新
     */
    void editJmChkstdTfs(JmChkstdTfDTO jmChkstdTfDTO);

}
