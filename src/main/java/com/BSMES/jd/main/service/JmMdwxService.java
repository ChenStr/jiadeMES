package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmBomMfDTO;
import com.BSMES.jd.main.dto.JmMdwx;
import com.BSMES.jd.main.dto.JmMdwxDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmMdwxEntity;

import java.util.List;

public interface JmMdwxService extends BaseService<JmMdwxEntity, JmMdwxDTO> {

    /**
     * 获取检验单信息
     * @param dto
     * @return
     */
    public CommonReturn getJmMdwx(ResultType dto);

    /**
     * 新增检验单信息
     */
    public CommonReturn saveJmMdwx(JmMdwx dto);


    /**
     * 编辑
     * @param dto
     * @return
     */
    public CommonReturn editJmMdwx(JmMdwxDTO dto);


    /**
     * 删除检验单信息
     */
    public CommonReturn delJmMdwx(List<String> sid);

    /**
     * 获取全部的物料主表 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getJmMdwxPage(ResultType dto);





}
