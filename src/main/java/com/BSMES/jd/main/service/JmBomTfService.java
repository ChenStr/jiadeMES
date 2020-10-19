package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmBomMfDTO;
import com.BSMES.jd.main.dto.JmBomTfDTO;
import com.BSMES.jd.main.entity.JmBomTfEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public interface JmBomTfService extends BaseService<JmBomTfEntity , JmBomTfDTO> {

    public CommonReturn getBomTf(JmBomTfDTO dto);

    public CommonReturn saveBomTf(JmBomTfDTO dto);

    public CommonReturn saveBomTfs(List<JmBomTfDTO> dtos);

    public CommonReturn editBomTf(JmBomTfDTO dto);

    public CommonReturn delBomTf(List<String> bomNos,List<Integer> itms);

    /**
     * 获取全部的物料表身 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getBomTfPage(JmBomTfDTO dto, QueryWrapper queryWrapper);

}
