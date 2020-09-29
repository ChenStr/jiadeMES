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
    public String mdNo;

    /**
     * 工装类型
     */
    @TableId
    public Integer typeid;

    /**
     * 名称
     */
    public String name;

    /**
     * 数量
     */
    public BigDecimal mdQty;

    /**
     * 模具分类
     */
    public String mdGrp;

    /**
     * 状态
     */
    public String state;

    /**
     * 仓库
     */
    public String wh;

    /**
     * 储位
     */
    public String whLoc;

    /**
     * 寿命次数
     */
    public BigDecimal qtyMax;

    /**
     * 累计使用次数
     */
    public BigDecimal qtyMk;

    /**
     * 寿命时间
     */
    public BigDecimal timeMax;

    /**
     * 累计加工时间
     */
    public BigDecimal timeMk;

    /**
     * 领用部门
     */
    public String dep;

}
