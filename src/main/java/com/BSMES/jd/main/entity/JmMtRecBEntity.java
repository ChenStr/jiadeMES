package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 设备维修保养记录子表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_mt_rec_b")
public class JmMtRecBEntity {

    /**
     * 单号
     */
    @TableId
    public String wxNo;

    /**
     * 项次
     */
    @TableId
    public Integer cid;

    /**
     * 数量
     */
    public BigDecimal qty;

    /**
     * 备注
     */
    public String rem;

    /**
     * 部件名称
     */
    public String partsName;

    /**
     * 修或换
     */
    public String prcId;

    /**
     * 修换件名称
     */
    public String prcName;

}
