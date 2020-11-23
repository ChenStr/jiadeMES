package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.InsgwcodeDTO;
import com.BSMES.jd.main.dto.JmChkSpcDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmChkSpcEntity;

import java.util.List;

public interface JmChkSpcService extends BaseService<JmChkSpcEntity, JmChkSpcDTO> {

    public CommonReturn getJmChkSpc(ResultType dto);

    public CommonReturn saveJmChkSpc(JmChkSpcDTO dto);

    public CommonReturn editJmChkSpc(JmChkSpcDTO dto);

    public CommonReturn delJmChkSpc(List<String> spcChks);

    /**
     * 获取全部的部门 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getJmChkSpcPage(ResultType dto);

}
