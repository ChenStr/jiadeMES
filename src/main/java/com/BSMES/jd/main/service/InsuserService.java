package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.InsuserDTO;
import com.BSMES.jd.main.entity.InsuserEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface InsuserService extends BaseService<InsuserEntity, InsuserDTO> {

    public CommonReturn getUser(InsuserDTO dto);

    public CommonReturn saveUser(InsuserDTO dto);

    public CommonReturn editUser(InsuserDTO dto);

    public CommonReturn delUser(List<String> usrcodes);

    /**
     * 获取全部的用户 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getUserPage(InsuserDTO dto, QueryWrapper queryWrapper);

}
