package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmMouldDTO;
import com.BSMES.jd.main.dto.JmMtIdDTO;
import com.BSMES.jd.main.entity.JmMtIdEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface JmMtIdService extends BaseService<JmMtIdEntity , JmMtIdDTO> {

    public CommonReturn getMtId(JmMtIdDTO dto);

    public CommonReturn saveMtId(JmMtIdDTO dto);

    public CommonReturn editMtId(JmMtIdDTO dto);

    public CommonReturn delMtId( List<String> mtIds );

    /**
     * 获取全部的检点项目表 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getMtIdPage(JmMtIdDTO dto, QueryWrapper queryWrapper);

}
