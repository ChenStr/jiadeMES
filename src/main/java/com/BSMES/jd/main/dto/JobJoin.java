package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class JobJoin extends BaseDTO implements Serializable {

    /**
     * 调度单号
     */
    public String sid;

    /**
     * 项次
     */
    public Integer cid;

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
     * 已生产数量(要去jobrec_b.qty_ok求和求出来)
     */
    public BigDecimal qtyAlready;

    /**
     * 发货量
     */
    public BigDecimal qtyShip;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date begDd;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date endDd;

    /**
     * 原料
     */
    public List<JmPrdtDTO> prdts;



    /**
     * 状态
     */
    public String state;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date createDate;

    /**
     * 模具编号
     */
    public String mdNo;

    /**
     * 产品规格
     */
    public String spc;

    /**
     * 产品代号
     */
    public String prdNo;

    /**
     * 设备代号
     */
    public String rsNo;

    /**
     * 部门代号
     */
    public String sorg;


}
