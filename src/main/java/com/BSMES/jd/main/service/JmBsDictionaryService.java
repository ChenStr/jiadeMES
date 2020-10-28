package com.BSMES.jd.main.service;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.JmBsDictionaryDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmBsDictionaryEntity;

import java.util.List;

public interface JmBsDictionaryService extends BaseService<JmBsDictionaryEntity, JmBsDictionaryDTO> {

    public CommonReturn getDictionary(ResultType dto);

    public CommonReturn saveDictionary(JmBsDictionaryDTO dto);

    public CommonReturn editDictionary(JmBsDictionaryDTO dto);

    /**
     * 虚假的删除
     * @param dto
     * @return
     */
    public CommonReturn delfalseDictionary(JmBsDictionaryDTO dto);

    public CommonReturn delDictionary(List<String> codes);

    /**
     * 获取全部的部门 分页
     * @param dto queryWrapper 条件
     * @return
     */
    public CommonReturn getDictionaryPage(ResultType dto);

}
