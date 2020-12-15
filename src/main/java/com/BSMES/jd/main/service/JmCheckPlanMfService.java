package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmCheckPlan;
import com.BSMES.jd.main.dto.JmCheckPlanMfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmCheckPlanMfEntity;

import java.util.List;

public interface JmCheckPlanMfService extends BaseService<JmCheckPlanMfEntity,JmCheckPlanMfDTO> {

    public CommonReturn getPlanMf(ResultType dto);

    public CommonReturn savePlanMf(JmCheckPlan dto);

    public CommonReturn editPlanMf(JmCheckPlanMfDTO dto);

    public CommonReturn delPlanMf(List<String> sids);

    /**
     * 获取全部的部门 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getPlanMfPage(ResultType dto);

}
