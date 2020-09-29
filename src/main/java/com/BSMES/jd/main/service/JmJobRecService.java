package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmJobRecDTO;
import com.BSMES.jd.main.entity.JmJobRecEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface JmJobRecService extends BaseService<JmJobRecEntity , JmJobRecDTO> {

    public CommonReturn getJobRec(JmJobRecDTO dto);

    public CommonReturn saveJobRec(JmJobRecDTO dto);

    public CommonReturn editJobRec(JmJobRecDTO dto);

    public CommonReturn delJobRec(List<String> opsids);

    /**
     * 获取全部的部门 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getJobRecPage(JmJobRecDTO dto, QueryWrapper queryWrapper);

}