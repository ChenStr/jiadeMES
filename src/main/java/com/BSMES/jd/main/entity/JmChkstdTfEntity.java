package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 检验标准子表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_chkstd_tf")
public class JmChkstdTfEntity {

    /**
     * 检验标准编码
     */
    public String chkstdNo;

    /**
     * 项次
     */
    public Integer cid;

    /**
     * 检验项目
     */
    public String chkNo;

    /**
     * 检验标准
     */
    public String chkPara;

    /**
     * 下限
     */
    public BigDecimal paraMin;

    /**
     * 上限
     */
    public BigDecimal paraMax;

    /**
     * 单位
     */
    public String paraUnit;

    /**
     * 检验工具
     */
    public String tlNo;

    /**
     * 检验方法
     */
    public String chkRem;

    /**
     * 链接方法
     */
    public String chkFile;

    /**
     * 说明
     */
    public String rem;

    /**
     * 伟高测试关联项
     */
    public String tsNo;

    /**
     * 检验类型
     */
    public String chkId;
}
