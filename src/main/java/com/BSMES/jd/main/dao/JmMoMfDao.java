package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.JmMoMfDTO;
import com.BSMES.jd.main.dto.JmMoMfMore;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmMoMfEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JmMoMfDao extends BaseDao<JmMoMfEntity> {

    List<JmMoMfMore> getMoMfMore(ResultType dto);

}
