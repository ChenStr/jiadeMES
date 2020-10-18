package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.JmXjMf2;
import com.BSMES.jd.main.dto.JmXjMfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmXjMfEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JmXjMfDao extends BaseDao<JmXjMfEntity> {

    List<JmXjMf2> getJmXjMf2(ResultType dto);

    void saveJmXjMfs(List<JmXjMfDTO> dtos);

}
