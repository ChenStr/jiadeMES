package com.BSMES.jd.main.entity.erp;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ERP指令单主表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("MF_MO")
public class ErpMfMoEntity {

    /**
     * 指令单号
     */
    @TableId("MO_NO")
    public String MO_NO;

    /**
     * 制单日期
     */
    @TableField("MO_DD")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date MO_DD;

    /**
     * 预开工日
     */
    @TableField("STA_DD")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date STA_DD;

    /**
     * 预完工日
     */
    @TableField("END_DD")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date END_DD;

    /**
     * 制造成品
     */
    @TableField("MRP_NO")
    public String MRP_NO;

    /**
     * 库位
     */
    @TableField("WH")
    public String WH;

    /**
     * 单位
     */
    @TableField("UNIT")
    public String UNIT;

    /**
     * 数量
     */
    @TableField("QTY")
    public BigDecimal QTY;

    /**
     * 部门代号
     */
    @TableField("DEP")
    public String DEP;

    /**
     * 结案
     */
    @TableField("CLOSE_ID")
    public String CLOSE_ID;

    /**
     * 制单人
     */
    @TableField("USR")
    public String USR;

    /**
     * 审核注记
     */
    @TableField("CHK_MAN")
    public String CHK_MAN;

    /**
     * 摘要
     */
    @TableField("REM")
    public String REM;

    /**
     * 终审日期
     */
    @TableField("CLS_DATE")
    public Date CLS_DATE;

    /**
     * 配方号
     */
    @TableField("ID_NO")
    public String ID_NO;

    /**
     * 输单日期
     */
    @TableField("SYS_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date SYS_DATE;

    /**
     * 领料按缴库
     */
    @TableField("ML_BY_MM")
    public String ML_BY_MM;

    /**
     * 发放生产标志
     */
    @TableField("CF_ID")
    public String CF_ID;
}
