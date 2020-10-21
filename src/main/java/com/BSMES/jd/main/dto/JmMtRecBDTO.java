package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 设备维修保养记录子表
 */
@Data
public class JmMtRecBDTO extends BaseDTO {

    /**
     * 单号
     */
    public String wxNo;

    /**
     * 项次
     */
    public Integer cid;

    /**
     * 数量
     */
    public BigDecimal qty;

    /**
     * 备注
     */
    public String rem;

    /**
     * 部件名称
     */
    public String partName;

    /**
     * 修或换
     */
    public String prcId;

    /**
     * 修换件名称
     */
    public String prcName;

}
