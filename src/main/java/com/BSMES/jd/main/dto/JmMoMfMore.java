package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 调度单(制令单)更多信息映射
 */
@Data
public class JmMoMfMore extends BaseDTO implements Serializable {

    /**
     * 调度单信息
     */
    public JmMoMfDTO jmMoMfDTO;

    /**
     * 原料信息
     */
    public List<JmPrdtDTO> prdts;

    /**
     * 单位
     */
    public JmPrdtutDTO jmPrdtutDTO;

}
