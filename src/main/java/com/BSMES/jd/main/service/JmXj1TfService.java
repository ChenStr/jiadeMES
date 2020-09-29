package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmXj1TfDTO;
import com.BSMES.jd.main.dto.JmXjTfDTO;
import com.BSMES.jd.main.entity.JmXj1TfEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;


public interface JmXj1TfService extends BaseService<JmXj1TfEntity , JmXj1TfDTO> {

    public CommonReturn getXj1Tf(JmXj1TfDTO dto);

    public CommonReturn saveXj1Tf(JmXj1TfDTO dto);

    public CommonReturn editXj1Tf(JmXj1TfDTO dto);

    public CommonReturn delXj1Tf(List<String> sids);

    /**
     * 获取全部的质检单 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getXj1TfPage(JmXj1TfDTO dto, QueryWrapper queryWrapper);

}
