package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmMtIdDTO;
import com.BSMES.jd.main.dto.JmMtstdMfDTO;
import com.BSMES.jd.main.entity.JmMtstdMfEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;


public interface JmMtstdMfService extends BaseService<JmMtstdMfEntity , JmMtstdMfDTO> {

    public CommonReturn getMtstdMf(JmMtstdMfDTO dto);

    public CommonReturn saveMtstdMf(JmMtstdMfDTO dto);

    public CommonReturn editMtstdMf(JmMtstdMfDTO dto);

    public CommonReturn delMtstdMf( List<String> mtstdNos );

    /**
     * 获取全部的检点项目表 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getMtstdMfPage(JmMtstdMfDTO dto, QueryWrapper queryWrapper);

}
