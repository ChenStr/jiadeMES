package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmBomMfDTO;
import com.BSMES.jd.main.dto.JmMtdd;
import com.BSMES.jd.main.dto.JmMtddMfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmMtddMfEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface JmMtddMfService extends BaseService<JmMtddMfEntity, JmMtddMfDTO> {

    public CommonReturn getMtdd(ResultType dto);

    public CommonReturn getMtddPlus(ResultType dto);

//    public CommonReturn getMtddReport(JmMtddMfDTO dto);

    public CommonReturn saveMtdd(JmMtdd dto);

    public CommonReturn editMtdd(JmMtddMfDTO dto);

    public CommonReturn delMtdd(List<String> sids);

    /**
     * 获取全部的物料主表 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getMtddPage(ResultType dto);

}
