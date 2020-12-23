package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.JmGoodsCheckTfDTO;
import com.BSMES.jd.main.entity.JmGoodsCheckTfEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JmGoodsCheckTfDao extends BaseDao<JmGoodsCheckTfEntity> {

    public void saveGoodTfS(List<JmGoodsCheckTfDTO> dtos);

    public void editGoodTfS(JmGoodsCheckTfDTO dto);

}
