package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.InssysvarDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.InsorgEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface InsorgService extends BaseService<InsorgEntity , InsorgDTO> {

    public CommonReturn getSorg(ResultType dto);

    public CommonReturn saveSorg(InsorgDTO dto);

    public CommonReturn editSorg(InsorgDTO dto);

    public CommonReturn delSorg(List<String> snames);

    /**
     * 获取全部的部门 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getSorgPage(ResultType dto);

    public InsorgDTO getTest(String id);

}
