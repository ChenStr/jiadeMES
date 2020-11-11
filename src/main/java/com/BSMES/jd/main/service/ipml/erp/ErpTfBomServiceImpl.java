package com.BSMES.jd.main.service.ipml.erp;

import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.erp.ErpTfBomDao;
import com.BSMES.jd.main.dto.erp.ErpMfBomDTO;
import com.BSMES.jd.main.dto.erp.ErpTfBomDTO;
import com.BSMES.jd.main.dto.erp.ErpTfMoDTO;
import com.BSMES.jd.main.entity.erp.ErpMfBomEntity;
import com.BSMES.jd.main.entity.erp.ErpTfBomEntity;
import com.BSMES.jd.main.service.erp.ErpMfBomService;
import com.BSMES.jd.main.service.erp.ErpTfBomService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ErpTfBomServiceImpl extends BaseServiceImpl<ErpTfBomDao, ErpTfBomEntity, ErpTfBomDTO> implements ErpTfBomService {

    @Autowired
    ErpMfBomService erpMfBomService;

    @Override
    public void beforeInsert(ErpTfBomDTO dto) {

    }

    @Override
    public void beforEedit(ErpTfBomDTO dto) {

    }
}
