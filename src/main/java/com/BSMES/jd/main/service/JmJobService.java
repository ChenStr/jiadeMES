package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmJobDTO;
import com.BSMES.jd.main.entity.JmJobEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface JmJobService extends BaseService<JmJobEntity , JmJobDTO> {

    public CommonReturn getJob(JmJobDTO dto);

    public CommonReturn saveJob(JmJobDTO dto);

    public CommonReturn editJob(JmJobDTO dto);

    public CommonReturn delJob(List<String> sids,List<Integer> cids);

    /**
     * 获取全部的生产计划单 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getJobPage(JmJobDTO dto, QueryWrapper queryWrapper);

}
