package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmMtIdDTO;
import com.BSMES.jd.main.dto.JmMtRec;
import com.BSMES.jd.main.dto.JmMtRecDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmMtRecEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;


public interface JmMtRecService extends BaseService<JmMtRecEntity , JmMtRecDTO> {

    public CommonReturn getMtRec(ResultType dto);

    public CommonReturn saveMtRec(JmMtRec dto);

    public CommonReturn editMtRec(JmMtRecDTO dto);

    public CommonReturn delMtRec( List<String> wxNos );

    /**
     * 获取全部的检点项目表 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getMtRecPage(ResultType dto);

}
