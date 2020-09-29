package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobJoin extends BaseDTO implements Serializable {

    /**
     * 计划单号
     */
    public String jbNo;

    /**
     * 车间名称
     */
    public String dep;

    /**
     * 设备名称
     */
    public String devName;

    /**
     * 模具名称
     */
    public String mouldName;

    /**
     * 产品名称
     */
    public String prdName;

    /**
     * 单位
     */
    public String umc;

    /**
     * 车间计划生产数量
     */
    public BigDecimal qtyAst;

    /**
     * 本次计划生产数量
     */
    public BigDecimal qtyPlan;

    /**
     * 已生产数量
     */
    public BigDecimal qtyAlready;

    /**
     * 发货量
     */
    public BigDecimal qtyShip;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date begDd;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date endDd;

    /**
     * 原料
     */
    public String rawName;

    /**
     * 原料规格
     */
    public String rawSpc;

    /**
     * 状态
     */
    public String state;

    /**
     * 创建时间
     */
    public Date createDate;

}
