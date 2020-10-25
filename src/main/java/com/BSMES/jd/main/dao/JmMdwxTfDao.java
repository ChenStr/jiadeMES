package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.JmMdwxTfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmMdwxTfEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JmMdwxTfDao extends BaseDao<JmMdwxTfEntity> {


    public void saveJmMdwxTfs(List<JmMdwxTfDTO> dtos);

    public void editJmMdwxTf(JmMdwxTfDTO dto);


}
