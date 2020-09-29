package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmXjTfDTO;
import com.BSMES.jd.main.entity.JmXjTfEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface JmXjTfService extends BaseService<JmXjTfEntity , JmXjTfDTO> {

    public CommonReturn getXjTf(JmXjTfDTO dto);

    public CommonReturn saveXjTf(JmXjTfDTO dto);

    public CommonReturn saveXjTfs(List<JmXjTfDTO> dtos);

    public CommonReturn editXjTf(JmXjTfDTO dto);

    public CommonReturn delXjTf(List<String> sids , List<Integer> cids);

    /**
     * 获取全部的质检单 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getXjTfPage(JmXjTfDTO dto, QueryWrapper queryWrapper);

}
