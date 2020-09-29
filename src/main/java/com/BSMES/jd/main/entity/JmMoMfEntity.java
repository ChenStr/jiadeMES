package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 调度单(制令单)映射
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_mo_mf")
public class JmMoMfEntity {

    /**
     * 单据代号
     */
    @TableId
    public String sid;

    /**
     * 业务类型
     */
    public String sbuid;

    /**
     * 单据日期
     */
    public Date hpdate;

    /**
     * 成品代号
     */
    public String prdNo;

    /**
     * 数量
     */
    public BigDecimal qty;

    /**
     * 已缴数量
     */
    public BigDecimal qtyFin;

    /**
     * 计划开工日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date begDd;

    /**
     * 计划完工日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date endDd;

    /**
     * 实际开工日期
     */
    public Date staDd;

    /**
     * 实际完工日期
     */
    public Date finDd;

    /**
     * 仓库
     */
    public String wh;

    /**
     * 部门
     */
    public String dep;

    /**
     * 结案标志
     */
    public String closeId;

    /**
     * 订单号码
     */
    public String soNo;

    /**
     * 物料BOM代号
     */
    public String bomNo;

    /**
     * 工艺代号
     */
    public String bomNoZc;

    /**
     * ERP生产单号
     */
    public String moNoErp;

    /**
     * 需求日期
     */
    public Date estDd;

    /**
     * 制单人
     */
    public String smake;

    /**
     * 创建时间
     */
    public Date moditime;

    /**
     * 审核人
     */
    public String chkMan;

    /**
     * 状态
     */
    public Integer state;

    /**
     * 引用次数
     */
    public Integer creftimes;

    /**
     * 单位
     */
    public String unit;

    public String unitErp;

    /**
     * 准备状态
     */
    public Integer statePre;

    public String paraname;

    /**
     * 备注
     */
    public String rem;

    /**
     * 返修单号
     */
    public String rwId;

    public String control;

    /**
     * 单据类别
     */
    public String bilType;

    /**
     * 需求客户
     */
    public String cusNo;

    /**
     * 部门
     */
    public String sorg;

    public String cusName;

    /**
     * 成品名
     */
    public String prdName;

    public Integer estItm;

    public Integer mkItm;

    public Integer bomid;

    public Integer routingid;

    public Integer modid;

    public String prdMark;

    public Integer dmdel;

    public Integer printId;

    public Date pbDd;

    public Integer genkn;

    public String batNo;

    public String bilNo;

    public String rsGrp;

    public BigDecimal upJj;

    public String moNoAdd;

    public Integer smkz;

    public String mdNo;

    public String sebNo;

    /**
     * 已分配数量
     */
    public BigDecimal qtyAlled;

    /**
     * 车间调整数量
     */
    public BigDecimal qtyAst;

    /**
     * 车间调整日期
     */
    public Date astDd;

    /**
     * 车间下达(判断是否是MES下达的数据)
     */
    public Integer astRelease;
}
