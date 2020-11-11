package com.BSMES.jd.main.entity.erp;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * ERP Bom子表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("TF_BOM")
public class ErpTfBomEntity {

    @TableId("BOM_NO")
    public String BOM_NO;

    @TableField("ITM")
    public Integer ITM;

    @TableField("PRD_NO")
    public String PRD_NO;

    @TableField("NAME")
    public String NAME;

    @TableField("WH_NO")
    public String WH_NO;

    @TableField("UNIT")
    public String UNIT;

    @TableField("QTY")
    public BigDecimal QTY;

}
