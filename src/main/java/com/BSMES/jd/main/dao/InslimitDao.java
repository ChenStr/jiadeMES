package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.Inslimit;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.InslimitEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InslimitDao extends BaseDao<InslimitEntity> {

    public List<Inslimit> getInslimit(ResultType dto);

}
