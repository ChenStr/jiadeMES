package com.BSMES.jd.main.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * bom表表身映射
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_bom_tf")
public class JmBomTfEntity {

    /**
     * bom代号
     */
    @TableId
    public String bomNo;

    /**
     * bom表表身项次
     */
    @TableId
    public Integer itm;

    /**
     * 原料代号
     */
    public String prdNo;

    /**
     * 仓库
     */
    public String wh;

    /**
     * 单位
     */
    public String unit;

    /**
     * 数量
     */
    public BigDecimal qty;

    public BigDecimal losRto;

    public BigDecimal cst;

    public BigDecimal qtyBas;

    public String idNo;

    public String prdNoChg;

    public String unitErp;

    public String zcNo;

    public String prdMark;

    public Integer keyparts;

    public String kpDesc;

    public String feederspc;

    public String usinNo;

    public String zbNo;

}
