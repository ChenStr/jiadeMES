package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmDevDTO;
import com.BSMES.jd.main.dto.JmOutMouldDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmOutMouldEntity;

import java.util.List;

public interface JmOutMouldService extends BaseService<JmOutMouldEntity, JmOutMouldDTO> {

    public CommonReturn getOut(ResultType dto);

    public CommonReturn saveOut(JmOutMouldDTO dto);

    public CommonReturn editOut(JmOutMouldDTO dto);

    public CommonReturn delOut(List<String> sids);

    public CommonReturn getOutExcel(ResultType dto);

    /**
     * 获取全部的设备 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getOutPage(ResultType dto);

}
