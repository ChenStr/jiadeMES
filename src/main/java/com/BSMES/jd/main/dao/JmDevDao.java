package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.Report;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmDevEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 设备表(加工资源)的Dao层
 */
@Mapper
public interface JmDevDao extends BaseDao<JmDevEntity> {

    /**
     * 冲压车间看板接口
     */
    public List<Report> getlookbord(ResultType dto);



}
