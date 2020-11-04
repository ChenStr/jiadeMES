package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.InslimitDTO;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.InslimitEntity;

import java.util.List;

public interface InslimitService extends BaseService<InslimitEntity , InslimitDTO> {

    public CommonReturn getInslimit(ResultType dto);

    public CommonReturn saveInslimit(InslimitDTO dto);

    public CommonReturn editInslimit(InslimitDTO dto);

    public CommonReturn delInslimit(List<String> gwusers,List<String> menuids);

    /**
     * 获取全部的数据 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getInslimitPage(ResultType dto);

}
