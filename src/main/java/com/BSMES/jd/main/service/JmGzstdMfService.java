package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmGzstd;
import com.BSMES.jd.main.dto.JmGzstdMfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmGzstdMfEntity;

import java.util.List;

public interface JmGzstdMfService extends BaseService<JmGzstdMfEntity , JmGzstdMfDTO> {

    public CommonReturn getGzstd(ResultType dto);

    public CommonReturn saveGzstd(JmGzstd dto);

    public CommonReturn editGzstd(JmGzstdMfDTO dto);

    public CommonReturn delGzstd(List<String> gzstdNos);

    /**
     * 获取全部 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getGzstdPage(ResultType dto);

}
