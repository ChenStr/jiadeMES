package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmChkstdMfDTO;
import com.BSMES.jd.main.dto.JmChkstdTfDTO;
import com.BSMES.jd.main.entity.JmChkstdMfEntity;
import com.BSMES.jd.main.entity.JmChkstdTfEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface JmChkstdTfService extends BaseService<JmChkstdTfEntity, JmChkstdTfDTO> {

    public CommonReturn getChkstdTf(JmChkstdTfDTO dto);

    public CommonReturn saveChkstdTf(JmChkstdTfDTO dto);

    public CommonReturn saveChkstdTfs(List<JmChkstdTfDTO> dtos);

    public CommonReturn editChkstdTf(JmChkstdTfDTO dto);

    public CommonReturn delChkstdTf(List<String> chkstdNos,List<Integer> cid);

    /**
     * 获取全部的检验标准 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getChkstdTfPage(JmChkstdTfDTO dto, QueryWrapper queryWrapper);

//    public InsorgDTO getTest(String id);

}
