package com.BSMES.jd.main.service.erp;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmMoMfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.dto.erp.ErpMfMoDTO;
import com.BSMES.jd.main.entity.erp.ErpMfMoEntity;

import java.util.List;

public interface ErpMfMoService extends BaseService<ErpMfMoEntity, ErpMfMoDTO> {

    public CommonReturn getReturn(ResultType dto);

    public CommonReturn saveMfMo(ErpMfMoDTO dto);

    public CommonReturn editMfMo(ErpMfMoDTO dto);

    public CommonReturn delMfMo(List<String> MONOS);

}
