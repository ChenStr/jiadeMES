package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.InssysvarDTO;
import com.BSMES.jd.main.dto.InsuserDTO;
import com.BSMES.jd.main.entity.InssysvarEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface InssysvarService extends BaseService<InssysvarEntity , InssysvarDTO> {

    public CommonReturn getVar(InssysvarDTO dto);

    public CommonReturn saveVar(InssysvarDTO dto);

    public CommonReturn editVar(InssysvarDTO dto);

    public CommonReturn delVar(List<String> snames);

    /**
     * 获取全部的变量 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getVarPage(InssysvarDTO dto, QueryWrapper queryWrapper);

}
