package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 设备(加工资源)
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JmDevDTO extends BaseDTO implements Serializable {

    /**
     * 设备代号
     */
    private String devNo;

    /**
     * 名称
     */
    private String name;

    /**
     * 型号
     */
    private String spc;

    /**
     * 关联资源
     */
    private String rsNo;

    /**
     * 供应厂商
     */
    private String cusname;

    /**
     * 维护人员
     */
    private String mainNo;

    /**
     * 部门
     */
    private String dep;

    /**
     * 时间上限
     */
    private BigDecimal maxtime;

    /**
     * 数量上限
     */
    private BigDecimal maxqty;

    /**
     * 累计加工时间
     */
    private BigDecimal timeMk;

    /**
     * 累计加工数量
     */
    private BigDecimal qtyMk;

    /**
     * 状态
     */
    private String state;

    /**
     * 设备分类
     */
    private String devid;

    /**
     * 部门
     */
    private String sorg;

    /**
     * 图片
     */
    private String picture;

    /**
     * 位置
     */
    private String location;

    /**
     * IP地址
     */
    private String ip;
    
}
