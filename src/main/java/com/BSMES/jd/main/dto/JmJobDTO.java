package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    public String sid;

    /**
     * 项次
     */
    public Integer cid;

    /**
     * 任务单号
     */
    public String jbNo;

    public String tzNo;

    /**
     * 成品代号
     */
    public String prdNo;

    /**
     * 工序代号
     */
    public String zcNo;

    /**
     * 数量
     */
    public BigDecimal qty;

    /**
     * 计划开工
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date begDd;

    /**
     * 计划完工
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date endDd;

    /**
     * 加工资源
     */
    public String rsNo;

    /**
     * 作业人员
     */
    public String wkNo;

    /**
     * 模具
     */
    public String mdNo;

    /**
     * 状态
     */
    public String state;

    /**
     * 条码
     */
    public String barCode;

    /**
     * 做法描述
     */
    public String zcRem;

    /**
     * 已完工量
     */
    public BigDecimal qtyFin;

    /**
     * 实际开工
     */
    public Date staDd;

    /**
     * 实际完工
     */
    public Date finDd;

    /**
     * 部门名称
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
     * 承上工序
     */
    public String zcNoUp;

    /**
     * 转下工序
     */
    public String zcNoDn;

    /**
     * 加工时间
     */
    public BigDecimal usedTime;

    /**
     * 准备状态
     */
    public Integer statePre;

    /**
     * 标准加工时间
     */
    public BigDecimal capTime;

    /**
     * 报工否
     */
    public String finId;

    /**
     * 检验否
     */
    public String chkId;

    /**
     * 待加工量
     */
    public BigDecimal qtyPrc;

    /**
     * 合格量
     */
    public BigDecimal qtyOk;

    /**
     * 不合格量
     */
    public BigDecimal qtyLost;

    /**
     * 返工量
     */
    public BigDecimal qtyRw;

    public Date sDd;

    public Date eDd;

    public Date ssDd;

    public Date eeDd;

    /**
     * 作业说明文件
     */
    public String zcFile;

    /**
     * 部门
     */
    public String sorg;

    public String prdMark;

    /**
     * 加工中心
     */
    public String rsGrp;

    public Integer dmdel;

    /**
     * 班组
     */
    public String teamNo;

    public String isRsv;

    public Date rsvDd;

    /**
     * 需转移
     */
    public String mvId;

    /**
     * 工序完工品
     */
    public String prdNoZc;

    /**
     * 转出数量
     */
    public BigDecimal qtyMv;

    public String zopNo;

    /**
     * 发货量
     */
    public BigDecimal qtyShip;

    /**
     * 创建时间
     */
    public Date createDate;

    /**
     * 设备名称
     */
    public String devName;

    /**
     * 模具名称
     */
    public String mdName;

    /**
     * 产品名称
     */
    public String prdName;

    /**
     * 单位代号
     */
    public String unit;
}
