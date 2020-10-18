package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 巡检记录子表
 */
@Data
public class JmXj2TfDTO extends BaseDTO implements Serializable {
    /**
     * 编码
     */
    public String sid;

    /**
     * 项次
     */
    public Integer cid;

    /**
     * 任务单号
     */
    public String jbNo;

    /**
     * 检验描述
     */
    public String opRem;

    /**
     * 检验人员
     */
    public String salNo;

    /**
     * 记录日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date opDd;

    /**
     * 制令单序列号
     */
    public String prdSn;

    /**
     * 标志
     */
    public Integer dmdel;

    /**
     * 延时开始比较时间
     */
    public Integer delayB;

    /**
     * 采集结束比较时间
     */
    public Integer overE;

    /**
     * 上限
     */
    public BigDecimal paraMax;

    /**
     * 下限
     */
    public BigDecimal paraMin;

    /**
     * 检验标准
     */
    public String chkPara;

    /**
     * 检验名称
     */
    public String chkName;

    /**
     * 标准值
     */
    public BigDecimal chkValue;

    public String qtyMd;

    /**
     * 状态
     */
    public String state;

}
