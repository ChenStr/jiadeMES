package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmMtstdTf;
import com.BSMES.jd.main.dto.JmMtstdTfDTO;
import com.BSMES.jd.main.entity.JmMtstdTfEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

/**
 * 设备保养检点表身(维修保养项目)
 */
public interface JmMtstdTfService extends BaseService<JmMtstdTfEntity , JmMtstdTfDTO> {

    public CommonReturn getMtstdTf(JmMtstdTfDTO dto);

    public CommonReturn saveMtstdTf(JmMtstdTfDTO dto);

    /**
     * 批量添加
     * @return 
     */
    public CommonReturn saveMtstdTfs(List<JmMtstdTf> dtos);

    public CommonReturn editMtstdTf(JmMtstdTfDTO dto);

    public CommonReturn delMtstdTf(List<String> mtstdNos , List<Integer> cids);

    /**
     * 获取全部的保养检点表身 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getMtstdTfPage(JmMtstdTfDTO dto, QueryWrapper queryWrapper);

}
