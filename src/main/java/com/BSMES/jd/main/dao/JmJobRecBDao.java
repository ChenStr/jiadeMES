package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.JmJobDTO;
import com.BSMES.jd.main.dto.JmJobRecBDTO;
import com.BSMES.jd.main.dto.Report;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmJobRecBEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JmJobRecBDao extends BaseDao<JmJobRecBEntity> {

    void insertJobRecBs(List<JmJobRecBDTO> jmJobRecBs);

    void updateJobRecB(JmJobRecBDTO jmJobRecB);

    /**
     * 车间生产日报表
     */
    public List<Report> getJobRecReport(ResultType dto);

    /**
     * 人员生产月报表
     */
    public List<Report> getJobRecMonReport(ResultType dto);

    /**
     * 设备月生产报表
     */
    public List<Report> getJobRecRsNoMonReport(ResultType dto);

}
