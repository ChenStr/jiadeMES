package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.JmMdlyTfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmMdlyTfEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JmMdlyTfDao extends BaseDao<JmMdlyTfEntity> {

    public void insertMdlys(List<JmMdlyTfDTO> dtos);

    public void editMdly(JmMdlyTfDTO dto);


}
