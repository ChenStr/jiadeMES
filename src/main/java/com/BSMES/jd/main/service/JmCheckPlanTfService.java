package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmCheckItemDTO;
import com.BSMES.jd.main.dto.JmCheckPlanTfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmCheckPlanTfEntity;

import java.util.List;

public interface JmCheckPlanTfService extends BaseService<JmCheckPlanTfEntity, JmCheckPlanTfDTO> {

    public CommonReturn getPlanTf(ResultType dto);

    public CommonReturn savePlanTf(List<JmCheckPlanTfDTO> dtos);

    public CommonReturn editPlanTf(JmCheckPlanTfDTO dto);

    public CommonReturn delPlanTf(List<String> sids,List<Integer> cids);

    /**
     * 获取全部的部门 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getPlanTfPage(ResultType dto);

}
