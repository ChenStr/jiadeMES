package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmJobRecBDTO;
import com.BSMES.jd.main.dto.JmJobRecDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmJobRecBEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface JmJobRecBService extends BaseService<JmJobRecBEntity , JmJobRecBDTO> {

    public CommonReturn getJobRecB(ResultType dto);

    public CommonReturn saveJobRecB(JmJobRecBDTO dto);

    public CommonReturn editJobRecB(JmJobRecBDTO dto);

    public CommonReturn editJobRecBs(List<JmJobRecBDTO> dtos);

    public CommonReturn delJobRecB(List<String> opsids,List<Integer> cids);

    /**
     * 批量插入多条
     */
    public CommonReturn saveJobRecBs(List<JmJobRecBDTO> dtos);

    /**
     * 获取全部的部门 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getJobRecBPage(ResultType dto);

    /**
     * 获取车间生产日报表
     */
    public CommonReturn getJobRecReportPage(ResultType dto);

    /**
     * 导出车间生产日报表 Excel 导出
     */
    public CommonReturn getSorgDayReportExcel(ResultType dto);

    /**
     * 人员生产月生产报表
     */
    public CommonReturn getJobRecMonReport(ResultType dto);

    /**
     * 人员生产月生产报表Excel 导出
     */
    public CommonReturn getUserMonReportExcel(ResultType dto);

    /**
     * 设备生产月报表
     */
    public CommonReturn getJobRecRsNoMonReport(ResultType dto);

    /**
     * 设备生产月报表 Excel 导出
     * @param dto
     * @return
     */
    public CommonReturn getDevMonReportExcel(ResultType dto);

}
