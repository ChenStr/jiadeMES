package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmMdbfMfDTO;
import com.BSMES.jd.main.dto.JmMdbfTfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmMdbfTfEntity;

import java.util.List;

public interface JmMdbfTfService extends BaseService<JmMdbfTfEntity, JmMdbfTfDTO> {

    public CommonReturn getMdbfTf(ResultType dto);

    public CommonReturn saveMdbfTfs(List<JmMdbfTfDTO> dtos);

    public CommonReturn editMdbfTf(JmMdbfTfDTO dto);

    public CommonReturn delMdbfTf(List<String> sids);

    /**
     * 获取全部的部门 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getMdbfTfPage(ResultType dto);

}
