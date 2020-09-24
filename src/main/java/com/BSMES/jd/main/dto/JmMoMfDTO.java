package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 调度单(制令单)映射
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JmMoMfDTO extends BaseDTO implements Serializable {

    /**
     * 单据代号
     */
    private String sid;

    /**
     * 业务类型
     */
    private String sbuid;

    /**
     * 单据日期
     */
    private Date hpdate;

    /**
     * 成品代号
     */
    private String prdNo;

    /**
     * 数量
     */
    private BigDecimal qty;

    /**
     * 已缴数量
     */
    private BigDecimal qtyFin;

    /**
     * 计划开工日期
     */
    private Date begDd;

    /**
     * 计划完工日期
     */
    private Date endDd;

    /**
     * 实际开工日期
     */
    private Date staDd;

    /**
     * 实际完工日期
     */
    private Date finDd;

    /**
     * 仓库
     */
    private String wh;

    /**
     * 部门
     */
    private String dep;

    /**
     * 结案标志
     */
    private String closeId;

    /**
     * 订单号码
     */
    private String soNo;

    /**
     * 物料BOM代号
     */
    private String bomNo;

    /**
     * 工艺代号
     */
    private String bomNoZc;

    /**
     * ERP生产单号
     */
    private String moNoErp;

    /**
     * 需求日期
     */
    private Date estDd;

    /**
     * 制单人
     */
    private String smake;

    /**
     * 创建时间
     */
    private Date moditime;

    /**
     * 审核人
     */
    private String chkMan;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 引用次数
     */
    private Integer creftimes;

    /**
     * 单位
     */
    private String unit;

    private String unitErp;

    /**
     * 准备状态
     */
    private Integer statePre;

    private String paraname;

    /**
     * 备注
     */
    private String rem;

    /**
     * 返修单号
     */
    private String rwId;

    private String control;

    /**
     * 单据类别
     */
    private String bilType;

    /**
     * 需求客户
     */
    private String cusNo;

    /**
     * 部门
     */
    private String sorg;

    private String cusName;

    /**
     * 成品名
     */
    private String prdName;

    private Integer estItm;

    private Integer mkItm;

    private Integer bomid;

    private Integer routingid;

    private Integer modid;

    private String prdMark;

    private Integer dmdel;

    private Integer printId;

    private Date pbDd;

    private Integer genkn;

    private String batNo;

    private String bilNo;

    private String rsGrp;

    private BigDecimal upJj;

    private String moNoAdd;

    private Integer smkz;

    private String mdNo;

    private String sebNo;

    /**
     * 已分配数量
     */
    private BigDecimal qtyAlled;

    /**
     * 车间调整数量
     */
    private BigDecimal qtyAst;

    /**
     * 车间调整日期
     */
    private Date astDd;

    /**
     * 车间下达(判断是否是MES下达的数据)
     */
    private Integer astRelease;

}
