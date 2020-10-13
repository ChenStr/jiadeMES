package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 巡检单主表映射表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_xj_mf")
public class JmXjMfEntity {

    /**
     * 单据代号
     */
    @TableId
    public String sid;

    /**
     * 单据日期
     */
    public Date hpdate;

    /**
     * 业务类型
     */
    public String sbuid;

    /**
     * 检验人员
     */
    public String smake;

    /**
     * 更新时间
     */
    public Date moditime;

    /**
     * 审核人
     */
    public String chk_man;

    /**
     * 状态
     */
    public Integer state;

    /**
     * 引用次数
     */
    public Integer creftimes;

    /**
     * 加工资源
     */
    public String rsNo;

    /**
     * 调度单号
     */
    public String moNo;

    /**
     * 产品代号
     */
    public String prdNo;

    /**
     * 批号
     */
    public String batNo;

    /**
     * 工序代号
     */
    public String zcNo;

    /**
     * 检验标识
     */
    public String xjid;

    /**
     * 操作人员
     */
    public String wkNo;

    /**
     * 部门
     */
    public String sorg;

    /**
     * 箱码
     */
    public String zxCode;

    /**
     * 报工数量
     */
    public BigDecimal qtyCur;

    /**
     * 合格量
     */
    public BigDecimal qtyOk;

    /**
     * 不合格量
     */
    public BigDecimal qtyLost;

    /**
     * 不合格原因
     */
    public String spcChk;

    /**
     * 不合格原因及数量
     */
    public String spcBtn;

    /**
     * 处理意见
     */
    public String chkId;

    /**
     * 备注
     */
    public String opRem;

    /**
     * 处理结果
     */
    public String finJudge;

    /**
     * 细节描述
     */
    public String xjms;

    /**
     * 细节检定
     */
    public String xjjd;

    /**
     * 检验依据
     */
    public String testBo;

    /**
     * 模具代号
     */
    public String mdNo;

    /**
     * 首件确认
     */
    public String firstChk;

    /**
     * 外观
     */
    public String aspectChk;

    /**
     * 互换性
     */
    public String interChk;

    /**
     * 可焊性
     */
    public String welcChk;

    /**
     * 计划单号
     */
    public String jbNo;
}
