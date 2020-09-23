package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 生产计划表映射(其中原料通过sid->jm_mo_tf)
 * 已生产数量:qty_fin
 * 发货量新增
 * 开始结束时间、计划开工完工
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JmJobDTO extends BaseDTO implements Serializable {

    /**
     * 生产计划单号(与制令单号一样)
     */
    private String sid;

    /**
     * 项次
     */
    private Integer cid;

    /**
     * 任务单号
     */
    private String jbNo;

    private String tzNo;

    /**
     * 成品代号
     */
    private String prdNo;

    /**
     * 工序代号
     */
    private String zcNo;

    /**
     * 数量
     */
    private BigDecimal qty;

    /**
     * 计划开工
     */
    private Date begDd;

    /**
     * 计划完工
     */
    private Date endDd;

    /**
     * 加工资源
     */
    private String rsNo;

    /**
     * 作业人员
     */
    private String wkNo;

    /**
     * 模具
     */
    private String mdNo;

    /**
     * 状态
     */
    private String state;

    /**
     * 条码
     */
    private String barCode;

    /**
     * 做法描述
     */
    private String zcRem;

    /**
     * 已完工量
     */
    private BigDecimal qtyFin;

    /**
     * 实际开工
     */
    private Date staDd;

    /**
     * 实际完工
     */
    private Date finDd;

    /**
     * 部门代号
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
     * 承上工序
     */
    private String zcNoUp;

    /**
     * 转下工序
     */
    private String zcNoDn;

    /**
     * 加工时间
     */
    private BigDecimal usedTime;

    /**
     * 准备状态
     */
    private Integer statePre;

    /**
     * 标准加工时间
     */
    private BigDecimal capTime;

    /**
     * 报工否
     */
    private String finId;

    /**
     * 检验否
     */
    private String chkId;

    /**
     * 待加工量
     */
    private BigDecimal qtyPrc;

    /**
     * 合格量
     */
    private BigDecimal qtyOk;

    /**
     * 不合格量
     */
    private BigDecimal qtyLost;

    /**
     * 返工量
     */
    private BigDecimal qtyRw;

    private Date sDd;

    private Date eDd;

    private Date ssDd;

    private Date eeDd;

    /**
     * 作业说明文件
     */
    private String zcFile;

    /**
     * 部门
     */
    private String sorg;

    private String prdMark;

    /**
     * 加工中心
     */
    private String rsGrp;

    private Integer dmdel;

    /**
     * 班组
     */
    private String teamNo;

    private String isRsv;

    private Date rsvDd;

    /**
     * 需转移
     */
    private String mvId;

    /**
     * 工序完工品
     */
    private String prdNoZc;

    /**
     * 转出数量
     */
    private BigDecimal qtyMv;

    private String zopNo;

    /**
     * 发货量
     */
    private BigDecimal qtyShip;

}
