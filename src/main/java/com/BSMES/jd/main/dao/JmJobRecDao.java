package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.JmJobRecEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JmJobRecDao extends BaseDao<JmJobRecEntity> {

    public List<JobRec> getJobRec(JobRec jobRec);

    /**
     * 批量修改
     * @param jmJobRecDTOS
     */
    public void editJobRecs(List<JmJobRecDTO> jmJobRecDTOS);

    /**
     * 日报表
     */
    public List<Report> getDepMoth(ResultType dto);

}
