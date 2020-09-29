package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmPrdtDTO;
import com.BSMES.jd.main.dto.JmPrdtutDTO;
import com.BSMES.jd.main.entity.JmPrdtutEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface JmPrdtutService extends BaseService<JmPrdtutEntity , JmPrdtutDTO>{

    public CommonReturn getPrdtut(JmPrdtutDTO dto);

    public CommonReturn savePrdtut(JmPrdtutDTO dto);

    public CommonReturn editPrdtut(JmPrdtutDTO dto);

    public CommonReturn delPrdtut(List<String> ubms);

    /**
     * 获取全部的单位 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getPrdtutPage(JmPrdtutDTO dto, QueryWrapper queryWrapper);

}
