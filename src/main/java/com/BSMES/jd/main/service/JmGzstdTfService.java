package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmGzstdTfDTO;
import com.BSMES.jd.main.dto.JmMdbfTfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmGzstdTfEntity;


import java.util.List;

public interface JmGzstdTfService extends BaseService<JmGzstdTfEntity , JmGzstdTfDTO> {

    public CommonReturn getGzstd(ResultType dto);

    public CommonReturn saveGzstds(List<JmGzstdTfDTO> dtos);

    public CommonReturn editGzstd(JmGzstdTfDTO dto);

    public CommonReturn delGzstd(List<String> gzstdNos, List<Integer> cids);

    /**
     * 获取全部的部门 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getGzstdPage(ResultType dto);

}
