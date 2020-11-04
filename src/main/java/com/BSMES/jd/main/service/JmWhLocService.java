package com.BSMES.jd.main.service;


import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.JmWhLocDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmWhLocEntity;

import java.util.List;

public interface JmWhLocService extends BaseService<JmWhLocEntity , JmWhLocDTO> {

    public CommonReturn getWhLoc(ResultType dto);

    public CommonReturn saveWhLoc(JmWhLocDTO dto);

    public CommonReturn editWhLoc(JmWhLocDTO dto);

    public CommonReturn delWhLoc(List<String> whLocs);

    /**
     * 获取全部的部门 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getWhLocPage(ResultType dto);

}
