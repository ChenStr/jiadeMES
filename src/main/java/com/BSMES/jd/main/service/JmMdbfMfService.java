package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmMdbf;
import com.BSMES.jd.main.dto.JmMdbfMfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmMdbfMfEntity;

import java.util.List;

/**
 * 模具报废
 */
public interface JmMdbfMfService extends BaseService<JmMdbfMfEntity, JmMdbfMfDTO> {

    public CommonReturn getMdbfMf(ResultType dto);

    /**
     * 保存主子关系
     * @param dto
     * @return
     */
    public CommonReturn saveMdbf(JmMdbf dto);

    public CommonReturn saveMdbfMf(JmMdbfMfDTO dto);

    public CommonReturn editMdbfMf(JmMdbfMfDTO dto);

    public CommonReturn delMdbfMf(List<String> sids);

    /**
     * 获取全部的部门 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getMdbfMfPage(ResultType dto);

}
