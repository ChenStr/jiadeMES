package com.BSMES.jd.main.service.ipml.erp;

import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.erp.ErpMfBomDao;
import com.BSMES.jd.main.dto.erp.ErpMfBomDTO;
import com.BSMES.jd.main.entity.erp.ErpMfBomEntity;
import com.BSMES.jd.main.service.erp.ErpMfBomService;
import org.springframework.stereotype.Service;

@Service
public class ErpMfBomServiceImpl extends BaseServiceImpl<ErpMfBomDao, ErpMfBomEntity, ErpMfBomDTO> implements ErpMfBomService {
    @Override
    public void beforeInsert(ErpMfBomDTO dto) {

    }

    @Override
    public void beforEedit(ErpMfBomDTO dto) {

    }
}
