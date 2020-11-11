package com.BSMES.jd.main.entity.erp;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ERP Bom主表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("MF_BOM")
public class ErpMfBomEntity {

    /**
     * BOM 号
     */
    @TableId("BOM_NO")
    public String BOM_NO;

    /**
     * 产品编号
     */
    @TableField("PRD_NO")
    public String PRD_NO;

}
