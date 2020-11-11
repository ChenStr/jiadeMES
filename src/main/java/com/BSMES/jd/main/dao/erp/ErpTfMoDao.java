package com.BSMES.jd.main.dao.erp;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.erp.ErpTfMoDTO;
import com.BSMES.jd.main.entity.erp.ErpTfMoEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ErpTfMoDao extends BaseDao<ErpTfMoEntity> {

    public void insertTfMoS(List<ErpTfMoDTO> dtos);

}
