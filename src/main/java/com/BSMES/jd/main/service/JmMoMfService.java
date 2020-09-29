package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.BaseDTO;
import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmMoMfDTO;
import com.BSMES.jd.main.entity.JmMoMfEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface JmMoMfService extends BaseService<JmMoMfEntity, JmMoMfDTO> {

    public CommonReturn getMoMf(JmMoMfDTO dto);

    public CommonReturn saveMoMf(JmMoMfDTO dto);

    public CommonReturn editMoMf(JmMoMfDTO dto);

    public CommonReturn delMoMf(List<String> sids);

    /**
     * 获取全部的调度单(制令单) 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getMoMfPage(JmMoMfDTO dto);

    /**
     * 公共编码规则
     * @param codeval
     * @param val
     * @return
     */
    public String getKey(String codeval,String val ,BaseService baseService, BaseDTO baseDTO);

}
