package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmGoodsCheck;
import com.BSMES.jd.main.dto.JmGoodsCheckMfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmGoodsCheckMfEntity;

import java.util.List;

public interface JmGoodsCheckMfService extends BaseService<JmGoodsCheckMfEntity, JmGoodsCheckMfDTO> {

    public CommonReturn getGoods(ResultType dto);

    public CommonReturn saveGoods(JmGoodsCheck dto);

    public CommonReturn editGoods(JmGoodsCheckMfDTO dto);

    public CommonReturn delGoods(List<String> sids);

    /**
     * 获取全部的设备 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getGoodsPage(ResultType dto);

}
