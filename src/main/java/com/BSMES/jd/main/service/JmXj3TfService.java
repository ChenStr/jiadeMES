package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmXj2TfDTO;
import com.BSMES.jd.main.dto.JmXj3TfDTO;
import com.BSMES.jd.main.entity.JmXj3TfEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface JmXj3TfService extends BaseService<JmXj3TfEntity , JmXj3TfDTO> {

    public CommonReturn getXj3Tf(JmXj3TfDTO dto);

    public CommonReturn saveXj3Tf(JmXj3TfDTO dto);

    public CommonReturn saveXj3Tfs(List<JmXj3TfDTO> dtos);

    public CommonReturn editXj3Tf(JmXj3TfDTO dto);

    public CommonReturn delXj3Tf(List<String> sids , List<Integer> cids ,List<String> chkNo);

    /**
     * 获取全部的保养检点表身 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getXj3TfPage(JmXj3TfDTO dto, QueryWrapper queryWrapper);

}
