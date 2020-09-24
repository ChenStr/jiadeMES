package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmWhDTO;
import com.BSMES.jd.main.entity.JmWhEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface JmWhService extends BaseService<JmWhEntity , JmWhDTO> {

    public CommonReturn getWh(JmWhDTO dto);

    public CommonReturn saveWh(JmWhDTO dto);

    public CommonReturn editWh(JmWhDTO dto);

    public CommonReturn delWh(List<String> whs);

    /**
     * 获取全部的仓库 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getWhPage(JmWhDTO dto, QueryWrapper queryWrapper);

}
