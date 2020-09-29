package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.JmJobDTO;
import com.BSMES.jd.main.dto.JobJoin;
import com.BSMES.jd.main.entity.JmJobEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JmJobDao extends BaseDao<JmJobEntity> {

    Integer insertJmJobs(List<JmJobDTO> jmJobs);

    void updateJob(JmJobDTO jmJob);

    List<JobJoin> joinFindJob(JobJoin jobJoin);



}
