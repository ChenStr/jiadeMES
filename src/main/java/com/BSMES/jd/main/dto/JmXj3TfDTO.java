package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 巡检记录子表
 */
@Data
public class JmXj3TfDTO extends BaseDTO implements Serializable {

    /**
     * 编码
     */
    public String sid;

    /**
     * 检验序号
     */
    public Integer cid;

    /**
     * 检验描述
     */
    public String chkNo;

    /**
     * 检验结果
     */
    public String curPara;

    /**
     * 判定结果
     */
    public Integer rlt;

    /**
     * 不合格分类
     */
    public String spcType;

    /**
     * 不及格原因
     */
    public String spcChk;

    /**
     * 检验记录
     */
    public String opRem;

    /**
     * 记录日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date opDd;

    /**
     * 标志
     */
    public Integer dmdel;

    /**
     * 偏差类型
     */
    public String derviId;

    /**
     * 偏差值
     */
    public BigDecimal derviation;

    /**
     * 检验值
     */
    public BigDecimal chkviation;
}
