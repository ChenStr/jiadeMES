package com.BSMES.jd.main.dto.erp;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * ERP Bom主表
 */
@Data
public class ErpMfBomDTO extends BaseDTO {

    /**
     * BOM 号
     */
    public String BOM_NO;

    public String _b_o_m__n_o;

    /**
     * 产品编号
     */
    public String PRD_NO;

    public String _p_r_d__n_o;

}
