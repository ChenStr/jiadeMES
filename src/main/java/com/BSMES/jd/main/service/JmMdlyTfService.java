package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmChkstdTfDTO;
import com.BSMES.jd.main.dto.JmMdlyTfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmMdlyTfEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface JmMdlyTfService extends BaseService<JmMdlyTfEntity, JmMdlyTfDTO> {

    public CommonReturn getMdlyTf(ResultType dto);

    public CommonReturn saveMdlyTf(JmMdlyTfDTO dto);

    public CommonReturn saveMdlyTfs(List<JmMdlyTfDTO> dtos);

    public CommonReturn editMdlyTf(JmMdlyTfDTO dto);

    public CommonReturn delMdlyTf(List<String> sids,List<Integer> cids);

    /**
     * 获取全部的检验标准 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getMdlyTfPage(ResultType dto);


}
