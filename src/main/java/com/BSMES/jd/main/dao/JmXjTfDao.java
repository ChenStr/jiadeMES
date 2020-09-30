package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.JmXjTfDTO;
import com.BSMES.jd.main.entity.JmXjTfEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JmXjTfDao extends BaseDao<JmXjTfEntity> {

    void insertJmXjTf(List<JmXjTfDTO> jmXjTfs);

    void updateJmXjTf(JmXjTfDTO jmXjTf);

}
