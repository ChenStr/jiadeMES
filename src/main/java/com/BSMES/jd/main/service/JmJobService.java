package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmJobDTO;
import com.BSMES.jd.main.dto.JobJoin;
import com.BSMES.jd.main.dto.JobSave;
import com.BSMES.jd.main.entity.JmJobEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface JmJobService extends BaseService<JmJobEntity , JmJobDTO> {

    public CommonReturn getJob(JmJobDTO dto);

    /**
     * 计划单展示
     * @param jobJoin
     * @return
     */
    public CommonReturn joinFindJobs(JobJoin jobJoin);

    /**
     * 暂时没有用到(公共的保存方法)
     * @param dto
     * @return
     */
    public CommonReturn saveJob(JmJobDTO dto);

    public CommonReturn saveJobs(JobSave jobSave);

    public CommonReturn editJob(JmJobDTO dto);

    public CommonReturn delJob(List<String> sids,List<Integer> cids);

    /**
     * 获取全部的生产计划单 分页(暂时没有用到)
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getJobPage(JmJobDTO dto, QueryWrapper queryWrapper);

    public CommonReturn getJobJoinPage(JobJoin jobJoin);

}
