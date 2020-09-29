package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 不合格原因表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_xj1_tf")
public class JmXj1TfEntity {

    /**
     * 单据代号
     */
    public String sid;

    /**
     * 项次
     */
    public Integer cid;

    /**
     * 不合格原因及代号
     */
    @TableId
    public String spcNo;

    /**
     * 不合格数量
     */
    public BigDecimal qty;

}
