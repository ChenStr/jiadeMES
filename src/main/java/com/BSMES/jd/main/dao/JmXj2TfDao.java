package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.JmXj2TfDTO;
import com.BSMES.jd.main.entity.JmXj2TfEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JmXj2TfDao extends BaseDao<JmXj2TfEntity> {

    //编辑方法
    void editJmXj2Tf(JmXj2TfDTO jmXj2TfDTO);

    //批量新增方法
    void saveJmXj2Tfs(List<JmXj2TfDTO> jmXj2TfDTOS);

}
