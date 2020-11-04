package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.InsgwcodeDTO;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.InsgwcodeEntity;

import java.util.List;

public interface InsgwcodeService extends BaseService<InsgwcodeEntity , InsgwcodeDTO> {

    public CommonReturn getInsgwcode(ResultType dto);

    public CommonReturn saveInsgwcode(InsgwcodeDTO dto);

    public CommonReturn editInsgwcode(InsgwcodeDTO dto);

    public CommonReturn delInsgwcode(List<String> gwcodes);

    /**
     * 获取全部的部门 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getInsgwcodePage(ResultType dto);

}
