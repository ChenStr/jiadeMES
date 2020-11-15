package com.BSMES.jd.main.dto.erp;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ERP指令单主表
 */
@Data
public class ErpMfMoDTO extends BaseDTO {

    /**
     * 指令单号
     */
    public String MO_NO;

    /**
     * 制单日期CLS_DATE
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public String MO_DD;

    /**
     * 预开工日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public String STA_DD;

    /**
     * 预完工日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public String END_DD;

    /**
     * 制造成品
     */
    public String MRP_NO;

    /**
     * 库位
     */
    public String WH;

    /**
     * 单位
     */
    public String UNIT;

    /**
     * 数量
     */
    public BigDecimal QTY;

    /**
     * 部门代号
     */
    public String DEP;

    /**
     * 结案
     */
    public String CLOSE_ID;

    /**
     * 制单人
     */
    public String USR;

    /**
     * 审核注记
     */
    public String CHK_MAN;

    /**
     * 摘要
     */
    public String REM;

    /**
     * 终审日期
     */
    public String CLS_DATE;

    /**
     * 配方号
     */
    public String ID_NO;

    /**
     * 输单日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public String SYS_DATE;

    /**
     * 领料按缴库
     */
    public String ML_BY_MM;

    /**
     * 发放生产标志
     */
    public String CF_ID;

}
