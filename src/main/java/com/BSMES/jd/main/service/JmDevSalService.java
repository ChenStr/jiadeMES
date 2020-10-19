package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.JmDevSalDTO;
import com.BSMES.jd.main.entity.JmDevSalEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface JmDevSalService extends BaseService<JmDevSalEntity , JmDevSalDTO> {

    public CommonReturn getDevSal(JmDevSalDTO dto);

    public CommonReturn saveDevSal(JmDevSalDTO dto);

    public CommonReturn editDevSal(JmDevSalDTO dto);

    public CommonReturn delDevSal(List<Integer> snames);

    /**
     * 获取全部的设备人员 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getDevSalPage(JmDevSalDTO dto, QueryWrapper queryWrapper);

}
