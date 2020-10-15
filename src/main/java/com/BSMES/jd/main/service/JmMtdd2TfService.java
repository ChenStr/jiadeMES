package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmMtdd2TfDTO;
import com.BSMES.jd.main.entity.JmMtdd2TfEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface JmMtdd2TfService extends BaseService<JmMtdd2TfEntity, JmMtdd2TfDTO> {

    public CommonReturn getMtdd2(JmMtdd2TfDTO dto);

    public CommonReturn saveMtdd2(JmMtdd2TfDTO dto);

    public CommonReturn editMtdd2(JmMtdd2TfDTO dto);

    public CommonReturn delMtdd2(List<String> mtstdNos,List<Integer> cids);

    /**
     * 获取全部的物料主表 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getMtdd2Page(JmMtdd2TfDTO dto, QueryWrapper queryWrapper);

}
