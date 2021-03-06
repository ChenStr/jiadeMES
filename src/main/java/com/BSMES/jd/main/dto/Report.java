package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 车间生产日报表
 */
@Data
public class Report extends BaseDTO {

    /**
     * 计划单编号
     */
    public String sid;

    /**
     * 计划单编号
     */
    public String jbNo;

    /**
     * 产品代号
     */
    public String prdNo;

    /**
     * 产品名称
     */
    public String prdName;

    /**
     * 部门
     */
    public String dep;

    /**
     * 人员代号
     */
    public String wkNo;

    /**
     * 人员名称
     */
    public String wkName;

    /**
     * 订单数量
     */
    public BigDecimal dqty;

    /**
     * 计划数量
     */
    public BigDecimal jqty;

    /**
     * 计划完成数
     */
    public BigDecimal wqty;

    /**
     * 当月完成计划数
     */
    public BigDecimal sqty;

    /**
     * 数量
     */
    public BigDecimal qty;

    /**
     * 设备代号
     */
    public String devNo;

    /**
     * 设备名称
     */
    public String devName;

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
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date hpdate;

}
