package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmMdbkMfDTO;
import com.BSMES.jd.main.dto.JmMdbkTfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmMdbkTfEntity;

import java.util.List;

public interface JmMdbkTfService extends BaseService<JmMdbkTfEntity, JmMdbkTfDTO> {

    public CommonReturn getMdbkTf(ResultType dto);

    public CommonReturn getMdbk(ResultType dto);

    public CommonReturn saveMdbkTf(JmMdbkTfDTO dto);

    public CommonReturn saveMdbkTfs(List<JmMdbkTfDTO> dtos);

    public CommonReturn editMdbkTf(JmMdbkTfDTO dto);

    public CommonReturn delMdbkTf(List<String> sids,List<Integer> cids);

    /**
     * 获取全部的子表 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getMdbkTfPage(ResultType dto);

}
