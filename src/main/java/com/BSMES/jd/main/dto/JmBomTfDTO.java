package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * bom表表身映射
 */
@Data
public class JmBomTfDTO extends BaseDTO implements Serializable {

    /**
     * bom代号
     */
    public String bomNo;

    /**
     * bom表表身项次
     */
    public Integer itm;

    /**
     * 原料代号
     */
    public String prdNo;

    /**
     * 仓库
     */
    public String wh;

    /**
     * 单位
     */
    public String unit;

    /**
     * 数量
     */
    public BigDecimal qty;

    public BigDecimal losRto;

    public BigDecimal cst;

    public BigDecimal qtyBas;

    public String idNo;

    public String prdNoChg;

    public String unitErp;

    public String zcNo;

    public String prdMark;

    public Integer keyparts;

    public String kpDesc;

    public String feederspc;

    public String usinNo;

    public String zbNo;

}
