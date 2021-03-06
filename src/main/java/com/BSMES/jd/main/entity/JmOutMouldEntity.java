package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 出模率明细
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_out_mould")
public class JmOutMouldEntity {

    @TableId
    public String sid;

    public String rsNo;

    public String mdNo;

    public String prdNo;

    public String prdName;

    public BigDecimal allMdNumber;

    public BigDecimal outMdNumber;

    public String mdMan;

    public String smake;

    public String dep;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date createTime;

    public String creator;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date updateTime;

    public String updator;

}
