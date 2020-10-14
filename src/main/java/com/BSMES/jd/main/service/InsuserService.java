package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.InsuserDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.InsuserEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface InsuserService extends BaseService<InsuserEntity, InsuserDTO> {

    public CommonReturn getUser(InsuserDTO dto);

    /**
     * 查找用户与人员的数据
     * @param dto
     * @return
     */
    public CommonReturn getUserPlus(InsuserDTO dto);

    /**
     * 登录(工位机与PC端的登录)
     * @param dto
     * @return
     */
    public CommonReturn login(InsuserDTO dto);

    public CommonReturn saveUser(InsuserDTO dto);

    public CommonReturn editUser(InsuserDTO dto);

    public CommonReturn delUser(List<String> usrcodes);

    /**
     * 获取全部的用户 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getUserPage(InsuserDTO dto);

}
