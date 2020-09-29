package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 产品表
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JmPrdtDTO extends BaseDTO implements Serializable {

    /**
     * 货品代号
     */
    public String prdNo;

    /**
     * 货品名称
     */
    public String name;

    /**
     * 简称
     */
    public String snm;

    /**
     * 货品大类
     */
    public String knd;

    /**
     * 中类
     */
    public String idx1;

    /**
     * 主单位
     */
    public String unit;

    /**
     * 规格
     */
    public String spc;

    /**
     * 安全存量
     */
    public BigDecimal qtyMin;

    /**
     * 缺省仓库
     */
    public String wh;

    /**
     * 标准成本
     */
    public BigDecimal cstStd;

    /**
     * ERP单位名称
     */
    public String ut;

    public Integer partid;

    /**
     * 参数列名
     */
    public String paraname;

    public String pk2Ut;

    public BigDecimal pk2Qty;

    public String pk3_ut;

    public String pk3_qty;

    public Integer rank;

    public Integer havesn;

    public Integer smcsHzzh;

    public String sbm;

    public BigDecimal upJj;

    public BigDecimal zxQty;

    public BigDecimal minWg;

    public BigDecimal maxWg;

}
