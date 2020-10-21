package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.JmDevSalDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmDevSalEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface JmDevSalService extends BaseService<JmDevSalEntity , JmDevSalDTO> {

    public CommonReturn getDevSal(ResultType dto);

    public CommonReturn saveDevSal(JmDevSalDTO dto);

    public CommonReturn saveDevSals(List<JmDevSalDTO> dtos);

    public CommonReturn editDevSal(JmDevSalDTO dto);

    public CommonReturn delDevSal(List<String> devNos,List<String> salNos);

    /**
     * 获取全部的设备人员 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getDevSalPage(ResultType dto);

}
