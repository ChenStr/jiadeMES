package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;


/**
 * 模具表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_mould")
public class JmMouldEntity {

    /**
     * 模具代号
     */
    @TableId
    private String mdNo;

    /**
     * 工装类型
     */
    @TableId
    private Integer typeid;

    /**
     * 名称
     */
    private String name;

    /**
     * 数量
     */
    private BigDecimal mdQty;

    /**
     * 模具分类
     */
    private String mdGrp;

    /**
     * 状态
     */
    private String state;

    /**
     * 仓库
     */
    private String wh;

    /**
     * 储位
     */
    private String whLoc;

    /**
     * 寿命次数
     */
    private BigDecimal qtyMax;

    /**
     * 累计使用次数
     */
    private BigDecimal qtyMk;

    /**
     * 寿命时间
     */
    private BigDecimal timeMax;

    /**
     * 累计加工时间
     */
    private BigDecimal timeMk;

    /**
     * 领用部门
     */
    private String dep;

}
