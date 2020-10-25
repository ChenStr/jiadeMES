package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmMdwxTfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmMdwxTfEntity;

import java.util.List;

public interface JmMdwxTfService extends BaseService<JmMdwxTfEntity, JmMdwxTfDTO> {


    public CommonReturn getJmMdwxTf(ResultType dto);

    /**
     * 批量插入
     */
    public CommonReturn insertJmMdwxTfs(List<JmMdwxTfDTO> dtos);

    /**
     * 修改
     */
    public CommonReturn editJmMdwxTf(JmMdwxTfDTO dto);

}
