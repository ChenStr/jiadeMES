package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmWorkerDTO;
import com.BSMES.jd.main.dto.JmXjMf2;
import com.BSMES.jd.main.dto.JmXjMfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmXjMfEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface JmXjMfService extends BaseService<JmXjMfEntity , JmXjMfDTO> {

    public CommonReturn getXjMf(ResultType dto);

    public CommonReturn getXjMfPlus(ResultType dto);

    /**
     * 首末检查
     * @param dto
     * @return
     */
    public CommonReturn getXjMfdetailed(ResultType dto);

    public CommonReturn saveXjMf(JmXjMf2 dto);

    public CommonReturn saveXjMfs(List<JmXjMfDTO> dtos);

    public CommonReturn editXjMf(JmXjMfDTO dto);

    public CommonReturn delXjMf(List<String> sids);

    /**
     * 获取全部的质检单 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getXjMfPage(ResultType dto);

    public CommonReturn getXjMfPlusPage(ResultType dto);

    /**
     * 定时任务 修改首末检的状态
     * @return
     */
    public CommonReturn getXjTime();

    public CommonReturn getJmxjAttach(ResultType dto);

    /**
     * 不良品导出
     * @return
     */
    public CommonReturn exportExcel(ResultType dto);

}
