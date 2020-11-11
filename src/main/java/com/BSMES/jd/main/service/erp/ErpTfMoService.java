package com.BSMES.jd.main.service.erp;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.erp.ErpTfMoDTO;
import com.BSMES.jd.main.entity.erp.ErpTfMoEntity;

import java.util.List;

public interface ErpTfMoService extends BaseService<ErpTfMoEntity, ErpTfMoDTO> {

    public CommonReturn saveTfMo(ErpTfMoDTO dto);

    public CommonReturn saveTfMos(List<ErpTfMoDTO> dtos);


    public CommonReturn editTfMo(ErpTfMoDTO dto);

    public CommonReturn delTfMo(List<String> MONOS,List<Integer> ITMS);

}
