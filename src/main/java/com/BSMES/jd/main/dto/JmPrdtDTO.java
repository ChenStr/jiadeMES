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
    private String prdNo;

    /**
     * 货品名称
     */
    private String name;

    /**
     * 简称
     */
    private String snm;

    /**
     * 货品大类
     */
    private String knd;

    /**
     * 中类
     */
    private String idx1;

    /**
     * 主单位
     */
    private String unit;

    /**
     * 规格
     */
    private String spc;

    /**
     * 安全存量
     */
    private BigDecimal qtyMin;

    /**
     * 缺省仓库
     */
    private String wh;

    /**
     * 标准成本
     */
    private BigDecimal cstStd;

    /**
     * ERP单位名称
     */
    private String ut;

    private Integer partid;

    /**
     * 参数列名
     */
    private String paraname;

    private String pk2Ut;

    private BigDecimal pk2Qty;

    private String pk3_ut;

    private String pk3_qty;

    private Integer rank;

    private Integer havesn;

    private Integer smcsHzzh;

    private String sbm;

    private BigDecimal upJj;

    private BigDecimal zxQty;

    private BigDecimal minWg;

    private BigDecimal maxWg;

}
