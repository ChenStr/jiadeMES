package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.JmMtRecBDTO;
import com.BSMES.jd.main.dto.JmMtRecDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmMtRecBEntity;

import java.util.List;


public interface JmMtRecBService extends BaseService<JmMtRecBEntity , JmMtRecBDTO> {

    public CommonReturn getMtRecB(ResultType dto);

    /**
     * 批量新增
     * @param dtos
     * @return
     */
    public CommonReturn saveMtRecBs(List<JmMtRecBDTO> dtos);

    public CommonReturn saveMtRecB(JmMtRecBDTO dto);

    public CommonReturn editMtRecB(JmMtRecBDTO dto);

    public CommonReturn delMtRecB( List<String> wxNos,List<Integer> cids);

    /**
     * 获取全部的检点项目表 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getMtRecBPage(ResultType dto);

}
