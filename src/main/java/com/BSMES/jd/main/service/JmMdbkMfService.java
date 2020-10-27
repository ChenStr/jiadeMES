package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.JmMdbk;
import com.BSMES.jd.main.dto.JmMdbkMfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmMdbkMfEntity;

import java.util.List;

public interface JmMdbkMfService extends BaseService<JmMdbkMfEntity , JmMdbkMfDTO> {

    public CommonReturn getMdbkMf(ResultType dto);

    /**
     *
     * @param dto
     * @return
     */
    public CommonReturn saveMdbk(JmMdbk dto);

    public CommonReturn saveMdbkMf(JmMdbkMfDTO dto);

    public CommonReturn editMdbkMf(JmMdbkMfDTO dto);

    public CommonReturn delMdbkMf(List<String> sids);

    /**
     * 获取全部的部门 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getMdbkMfPage(ResultType dto);

}
