package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmMtstdTfDTO;
import com.BSMES.jd.main.dto.JmXj2TfDTO;
import com.BSMES.jd.main.dto.JmXjMf;
import com.BSMES.jd.main.entity.JmXj2TfEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface JmXj2TfService extends BaseService<JmXj2TfEntity , JmXj2TfDTO> {

    public CommonReturn getXj2Tf(JmXj2TfDTO dto);

    public CommonReturn saveXj2Tf(JmXj2TfDTO dto);

    /**
     *
     * @param jmXjMf
     * @return
     */
    public CommonReturn saveXj2TfAndXj3Tf(JmXjMf jmXjMf);

    public CommonReturn saveXj2Tfs(List<JmXj2TfDTO> dtos);

    public CommonReturn editXj2Tf(JmXjMf dto);

    /**
     * 审核专用
     * @param dto
     * @return
     */
    public CommonReturn checkXj2Tf(JmXjMf dto);

    /**
     * 根据主表单个删除
     * @param sid
     * @return
     */
    public CommonReturn deleteXj2Tf(String sid);

    public CommonReturn delXj2Tf(List<String> sids , List<Integer> cids);

    /**
     * 获取全部的保养检点表身 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getXj2TfPage(JmXj2TfDTO dto, QueryWrapper queryWrapper);

}
