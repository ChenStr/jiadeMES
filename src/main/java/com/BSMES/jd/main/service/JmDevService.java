package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmDevDTO;
import com.BSMES.jd.main.entity.JmDevEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;


public interface JmDevService extends BaseService<JmDevEntity, JmDevDTO> {

    public CommonReturn getDev(JmDevDTO dto);

    public CommonReturn saveDev(JmDevDTO dto);

    public CommonReturn editDev(JmDevDTO dto);

    public CommonReturn delDev(List<String> wkNos);

    /**
     * 获取全部的设备 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getDevPage(JmDevDTO dto, QueryWrapper queryWrapper);

}
