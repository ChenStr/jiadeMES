package com.BSMES.jd.main.dto.erp;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

/**
 * ERP Bom子表
 */
@Data
public class ErpTfBomDTO extends BaseDTO {

    public String BOM_NO;

    public Integer ITM;

    public String PRD_NO;

    public String NAME;

    public String WH_NO;

    public String UNIT;

    public BigDecimal QTY;

}
