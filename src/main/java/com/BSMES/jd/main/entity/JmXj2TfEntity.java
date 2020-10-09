package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 巡检记录子表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_xj2_tf")
public class JmXj2TfEntity {

    /**
     * 编码
     */
    @TableId
    public String sid;

    /**
     * 项次
     */
    @TableId
    public Integer cid;

    /**
     * 任务单号
     */
    public String jbNo;

    /**
     * 判定结果
     */
    public Integer rlt;

    /**
     * 不合格分类
     */
    public String spcType;

    /**
     * 不合格原因
     */
    public String spcChk;

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

}