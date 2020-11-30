package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmXj2TfDTO;
import com.BSMES.jd.main.dto.JmXj3TfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmXj3TfEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface JmXj3TfService extends BaseService<JmXj3TfEntity , JmXj3TfDTO> {

    public CommonReturn getXj3Tf(ResultType dto);

    public CommonReturn saveXj3Tf(JmXj3TfDTO dto);

    public CommonReturn saveXj3Tfs(List<JmXj3TfDTO> dtos);

    public CommonReturn editXj3Tf(JmXj3TfDTO dto);

    /**
     * 根据主表单个删除
     * @param sid
     * @return
     */
    public CommonReturn deleteXj3Tf(String sid);

    public CommonReturn delXj3Tf(List<String> sids , List<Integer> cids ,List<String> chkNo);

    /**
     * 获取全部的保养检点表身 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getXj3TfPage(ResultType dto, QueryWrapper queryWrapper);

}
