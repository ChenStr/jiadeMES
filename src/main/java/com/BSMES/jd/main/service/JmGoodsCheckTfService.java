package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmGoodsCheckTfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmGoodsCheckTfEntity;

import java.util.List;

public interface JmGoodsCheckTfService extends BaseService<JmGoodsCheckTfEntity, JmGoodsCheckTfDTO> {

    public CommonReturn getGoods(ResultType dto);

    public CommonReturn saveGoods(List<JmGoodsCheckTfDTO> dtos);

    public CommonReturn editGoods(JmGoodsCheckTfDTO dto);

    public CommonReturn delGoods(List<String> sids,List<Integer> cids);

    /**
     * 获取全部的设备 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getGoodsPage(ResultType dto);

}
