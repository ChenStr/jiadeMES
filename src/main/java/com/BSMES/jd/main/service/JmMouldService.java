package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmMouldDTO;
import com.BSMES.jd.main.entity.JmMouldEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;


public interface JmMouldService extends BaseService<JmMouldEntity , JmMouldDTO> {

    public CommonReturn getMould(JmMouldDTO dto);

    public CommonReturn saveMould(JmMouldDTO dto);

    public CommonReturn editMould(JmMouldDTO dto);

    public CommonReturn delMould(List<String> sids , List<Integer> cids);

    /**
     * 获取全部的模具表 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getMoMfPage(JmMouldDTO dto, QueryWrapper queryWrapper);

}
