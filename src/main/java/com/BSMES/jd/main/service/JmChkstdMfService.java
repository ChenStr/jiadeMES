package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.JmChkstd;
import com.BSMES.jd.main.dto.JmChkstdMfDTO;
import com.BSMES.jd.main.entity.JmChkstdMfEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface JmChkstdMfService extends BaseService<JmChkstdMfEntity, JmChkstdMfDTO> {

    public CommonReturn getChkstdMf(JmChkstdMfDTO dto);

    /**
     * 保存表头与表身信息
     * @param dto
     * @return
     */
    public CommonReturn saveChkstd(JmChkstd dto);

    public CommonReturn editChkstdMf(JmChkstdMfDTO dto);

    public CommonReturn delChkstdMf(List<String> chkstdNos);

    /**
     * 获取全部的检验标准 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getChkstdMfPage(JmChkstdMfDTO dto,QueryWrapper queryWrapper);

//    public InsorgDTO getTest(String id);

}
