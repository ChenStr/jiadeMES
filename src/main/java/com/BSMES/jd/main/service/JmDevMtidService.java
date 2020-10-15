package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmDevMtidDTO;
import com.BSMES.jd.main.dto.JmMouldDTO;
import com.BSMES.jd.main.entity.JmDevMtidEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface JmDevMtidService extends BaseService<JmDevMtidEntity, JmDevMtidDTO> {

    public CommonReturn getDevMtid(JmDevMtidDTO dto);

    public CommonReturn saveDevMtid(JmDevMtidDTO dto);

    public CommonReturn editDevMtid(JmDevMtidDTO dto);

    public CommonReturn delDevMtid(List<String> devids , List<String> mtIds);

    /**
     * 获取全部的模具表 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getDevMtidPage(JmDevMtidDTO dto, QueryWrapper queryWrapper);

}
