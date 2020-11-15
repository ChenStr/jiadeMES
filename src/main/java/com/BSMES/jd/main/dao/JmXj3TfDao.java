package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.JmXj3TfDTO;
import com.BSMES.jd.main.entity.JmXj3TfEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JmXj3TfDao extends BaseDao<JmXj3TfEntity> {

    //批量新增
    void saveJmXj3Tfs(List<JmXj3TfDTO> jmXj3TfDTOS);

    //修改操作
    void editJmXj3Tf(JmXj3TfDTO jmXj3TfDTO);

}
