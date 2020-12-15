package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmCheckItemDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmCheckItemEntity;

import java.util.List;

public interface JmCheckItemService extends BaseService<JmCheckItemEntity, JmCheckItemDTO> {

    public CommonReturn getCheck(ResultType dto);

    public CommonReturn saveCheck(JmCheckItemDTO dto);

    public CommonReturn editCheck(JmCheckItemDTO dto);

    public CommonReturn delCheck(List<String> sids);

    /**
     * 获取全部的部门 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getCheckPage(ResultType dto);

}
