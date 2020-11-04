package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.InslimitDTO;
import com.BSMES.jd.main.dto.JmWxIdDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmWxIdEntity;

import java.util.List;


public interface JmWxIdService extends BaseService<JmWxIdEntity, JmWxIdDTO> {

    public CommonReturn getJmWxId(ResultType dto);

    public CommonReturn saveJmWxId(JmWxIdDTO dto);

    public CommonReturn editJmWxId(JmWxIdDTO dto);

    public CommonReturn delJmWxId(List<String> mtIds);

    /**
     * 获取全部的数据 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getJmWxIdPage(ResultType dto);

}
