package com.BSMES.jd.main.dto.erp;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

/**
 * ERP指令单子表
 */
@Data
public class ErpTfMoDTO extends BaseDTO {

    /**
     * 指令单号
     */
    public String MO_NO;

    /**
     * 项次
     */
    public Integer ITM;

    /**
     * 材料号
     */
    public String PRD_NO;

    /**
     * 材料名称
     */
    public String PRD_NAME;

    /**
     * 仓库
     */
    public String WH;

    /**
     * 单位
     */
    public String UNIT;

    /**
     * 应发量
     */
    public BigDecimal QTY_RSV;

    /**
     * 单据追踪项次
     */
    public Integer EST_ITM;

    /**
     * 历次变动项
     */
    public Integer PRE_ITM;

    /**
     * 标准用量
     */
    public BigDecimal QTY_STD;

}
