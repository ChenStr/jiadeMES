package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmWorkerDTO;
import com.BSMES.jd.main.entity.JmWorkerEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;


public interface JmWorkerService extends BaseService<JmWorkerEntity, JmWorkerDTO> {

    public CommonReturn getWorker(JmWorkerDTO dto);

    public CommonReturn saveWorker(JmWorkerDTO dto);

    public CommonReturn editWorker(JmWorkerDTO dto);

    public CommonReturn delWorker(List<String> wkNos);

    /**
     * 获取全部的人员 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getWorkerPage(JmWorkerDTO dto, QueryWrapper queryWrapper);

}
