package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 来料检主表
 */
@Data
public class JmGoodsCheckMfDTO extends BaseDTO {

    public String sid;

    public String supplyId;

    public String supplyName;

    public String rawSpc;

    public String prdNo;

    public String prdName;

    public String planNo;

    public String planName;

    public BigDecimal qty;

    public String checkNo;

    public String spc;

    public BigDecimal num;

    public BigDecimal spotCheckQty;

    public String spotPlan;

    public BigDecimal spotQty;

    public String checkConclu;

    public String lostOpinion;

    public String rem;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date approachTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date productTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date createTime;

    public String creator;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date updateTime;

    public String updator;

    public String reserved;

}
