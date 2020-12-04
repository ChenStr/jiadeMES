package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 随工单
 */
@Data
public class JmJobRecDTO extends BaseDTO implements Serializable {

    /**
     * 随工单编号
     */
    public String opsid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date opDd;

    public String usr;

    /**
     * 产品代号
     */
    public String prdNo;

    public String zcNo;

    public String opId;

    /**
     * 送检数量
     */
    public BigDecimal qtyCur;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    public Date opTime;

    public String rsNo;

    /**
     * 计划编号
     */
    public String jbNo;

    public String opRem;

    /**
     * 重量
     */
    public BigDecimal qty;

    /**
     * 废品数量
     */
    public BigDecimal qtyLost;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date endDd;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date staDd;

    public String wkNo;

    /**
     * 模具编号
     */
    public String mdNo;

    /**
     * 首尾模状态
     */
    public String statePre;

    /**
     * 是否合格
     */
    public String state;

    public String moNo;

    public String tmNo;

    public String wrNo;

    public Integer genId;

    public String prdMark;

    public String isRw;

    public String prdSn;

    public Integer dmdel;

    public String spcNo;

    public String boxNo;

    public String batNo;

    public BigDecimal qty1Cur;

    public BigDecimal qty1Lost;

    /**
     * 原料1名称
     */
    public String rmName1;
    /**
     * 原料2名称
     */
    public String rmName2;
    /**
     * 原料3名称
     */
    public String rmName3;
    /**
     * 原料4名称
     */
    public String rmName4;

    /**
     * 原料1投料数量
     */
    public BigDecimal qtyRm1;

    /**
     * 原料2投料数量
     */
    public BigDecimal qtyRm2;

    /**
     * 原料3投料数量
     */
    public BigDecimal qtyRm3;

    /**
     * 原料4投料数量
     */
    public BigDecimal qtyRm4;

    /**
     * 原料1批号
     */
    public String rmBn1;

    /**
     * 原料2批号
     */
    public String rmBn2;

    /**
     * 原料3批号
     */
    public String rmBn3;
    /**
     * 原料4批号
     */
    public String rmBn4;

    /**
     * ⚔🈚
     */
    public String firsMd;

    /**
     * 是否合格
     */
    public String stateOk;

    /**
     * 重量
     */
    public BigDecimal qtyWt;

    /**
     *
     */
    public String prdName;


}
