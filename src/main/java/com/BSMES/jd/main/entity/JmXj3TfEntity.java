package com.BSMES.jd.main.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 巡检记录子表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_xj3_tf")
public class JmXj3TfEntity {

    /**
     * 编码
     */
    @TableId
    public String sid;

    /**
     * 检验序号
     */
    @TableId
    public Integer cid;

    /**
     * 检验描述
     */
    @TableId
    public String chkNo;

    /**
     * 上限
     */
    public BigDecimal paraMax;

    /**
     * 下限
     */
    public BigDecimal paraMin;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date opDd;

    /**
     * 标志
     */
    public Integer dmdel;

}
