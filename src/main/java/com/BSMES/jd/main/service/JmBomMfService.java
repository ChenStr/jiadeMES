package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.BomPlus;
import com.BSMES.jd.main.dto.JmBomMfDTO;
import com.BSMES.jd.main.dto.JmDevDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmBomMfEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface JmBomMfService extends BaseService<JmBomMfEntity , JmBomMfDTO> {

    public CommonReturn getBomMf(JmBomMfDTO dto);

    /**
     * 获取表头表身的信息
     * @param dto
     * @return
     */
    public CommonReturn getBomPlus(ResultType dto);

    public CommonReturn saveBomMf(BomPlus dto);

    public CommonReturn editBomMf(JmBomMfDTO dto);

    public CommonReturn delBomMf(List<String> bomNos);

    /**
     * 获取全部的物料主表 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getBomMfPage(JmBomMfDTO dto);

    public CommonReturn getBomPlusPage(ResultType dto);

}
