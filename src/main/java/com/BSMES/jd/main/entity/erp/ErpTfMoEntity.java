package com.BSMES.jd.main.entity.erp;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * ERP指令单子表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("TF_MO")
public class ErpTfMoEntity {

    /**
     * 指令单号
     */
    @TableId("MO_NO")
    public String MO_NO;

    /**
     * 项次
     */
    @TableField("ITM")
    public Integer ITM;

    /**
     * 材料号
     */
    @TableField("PRD_NO")
    public String PRD_NO;

    /**
     * 材料名称
     */
    @TableField("PRD_NAME")
    public String PRD_NAME;

    /**
     * 仓库
     */
    @TableField("WH")
    public String WH;

    /**
     * 单位
     */
    @TableField("UNIT")
    public String UNIT;

    /**
     * 应发量
     */
    @TableField("QTY_RSV")
    public BigDecimal QTY_RSV;

    /**
     * 单据追踪项次
     */
    @TableField("EST_ITM")
    public Integer EST_ITM;

    /**
     * 历次变动项
     */
    @TableField("PRE_ITM")
    public Integer PRE_ITM;

    /**
     * 标准用量
     */
    @TableField("QTY_STD")
    public BigDecimal QTY_STD;
}
