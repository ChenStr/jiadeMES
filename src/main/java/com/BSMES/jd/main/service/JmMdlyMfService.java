package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.JmMdlyMfEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JmMdlyMfService extends BaseService<JmMdlyMfEntity , JmMdlyMfDTO> {

    public CommonReturn getMdlyMf(ResultType dto);

    /**
     * 表头表身一起保存
     * @param dto
     * @return
     */
    public CommonReturn saveMdlyMf(JmMdly dto);

    public CommonReturn editMdlyMf(JmMdlyMfDTO dto);

    public CommonReturn delMdlyMf(List<String> sids);

    /**
     * 获取全部的模具领用 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getMdlyMfPage(ResultType dto);

}
