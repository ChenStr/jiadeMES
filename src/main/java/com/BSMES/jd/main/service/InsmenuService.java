package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.InsmenuDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.InsmenuEntity;

public interface InsmenuService extends BaseService<InsmenuEntity , InsmenuDTO> {

    public CommonReturn getInsmenu(ResultType dto);

    /**
     * 获取全部的部门 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getInsmenuPage(ResultType dto);

}
