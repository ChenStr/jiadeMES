package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmJobRecDTO;
import com.BSMES.jd.main.dto.JobRec;
import com.BSMES.jd.main.dto.JobRecSave;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmJobRecEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface JmJobRecService extends BaseService<JmJobRecEntity , JmJobRecDTO> {

    public CommonReturn getJobRec(ResultType dto);

    public CommonReturn getJobRecs(JobRec jobRec);

    public CommonReturn saveJobRec(JmJobRecDTO dto);

    public CommonReturn saveJobRecAndRecB(JobRecSave jobRecSave);

    public CommonReturn editJobRec(JmJobRecDTO dto);

    /**
     * 定时任务 修改随工单状态
     * @return
     */
    public CommonReturn taskeditJobRec();

    public CommonReturn delJobRec(List<String> opsids);

    /**
     * 获取全部的部门 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getJobRecPage(ResultType dto);


    public CommonReturn getJobRecsPage(JobRec jobRec);

    /**
     * 车间生产月报表导出
     * @param dto
     * @return
     */
    public CommonReturn getDepMoth(ResultType dto) throws IOException, ParseException;
}
