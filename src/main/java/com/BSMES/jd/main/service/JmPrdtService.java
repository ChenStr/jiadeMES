package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmPrdtDTO;
import com.BSMES.jd.main.entity.JmPrdtEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface JmPrdtService extends BaseService<JmPrdtEntity , JmPrdtDTO> {

    public CommonReturn getPrdt(JmPrdtDTO dto);

    public CommonReturn savePrdt(JmPrdtDTO dto);

    public CommonReturn editPrdt(JmPrdtDTO dto);

    public CommonReturn delPrdt(List<String> prdNos);

    /**
     * 获取全部的货品 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getPrdtPage(JmPrdtDTO dto, QueryWrapper queryWrapper);

}
