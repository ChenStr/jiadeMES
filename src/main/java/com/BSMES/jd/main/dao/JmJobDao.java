package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.JmJobEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JmJobDao extends BaseDao<JmJobEntity> {

    Integer insertJmJobs(List<JmJobDTO> jmJobs);

    void updateJob(JmJobDTO jmJob);

    List<JobJoin> joinFindJob(JobJoin jobJoin);

    /**
     * 车间生产月报表
     */
    public List<Report> getJmJobReport(ResultType dto);

    /**
     * 车间下单量 折线图
     */
    public List<Report> getsorgSum(ResultType dto);

    /**
     * 车间产量 折线图
     */
    public List<Report> getsorgYield(ResultType dto);

    /**
     * 车间良品率
     */
    public List<Report> getGood(ResultType dto);



}
