package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class JobRec extends BaseDTO {

    public String sid;

    public String cid;

    /**
     * 随工单编号
     */
    public String opsid;

    /**
     * 车间名称
     */
    public String dep;

    /**
     * 设备名称
     */
    public String devName;

    /**
     * 模具代号
     */
    public String mdNo;

    /**
     * 模具名称
     */
    public String mdName;

    /**
     * 产品代号
     */
    public String prdNo;

    /**
     * 产品名称
     */
    public String prdName;

    /**
     * 计划产量
     */
    public BigDecimal qty;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date begDd;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date endDd;

    /**
     * 合格数
     */
    public BigDecimal qtyOk;

    /**
     * 重量
     */
    public BigDecimal weight;

    /**
     * 原料名称
     */
    public String rmName1;

    /**
     * 原料名称
     */
    public String rmName2;

    /**
     * 原料名称
     */
    public String rmName3;

    /**
     * 原料名称
     */
    public String rmName4;

    /**
     * 投料数
     */
    public BigDecimal qtyRm1;

    /**
     * 投料数
     */
    public BigDecimal qtyRm2;

    /**
     * 投料数
     */
    public BigDecimal qtyRm3;

    /**
     * 投料数
     */
    public BigDecimal qtyRm4;

    /**
     * 原料批号
     */
    public String rmBn1;

    /**
     * 原料批号
     */
    public String rmBn2;

    /**
     * 原料批号
     */
    public String rmBn3;

    /**
     * 原料批号
     */
    public String rmBn4;

    /**
     * 生产状态
     */
    public String state;

    /**
     * 是否合格
     */
    public String stateOk;

    /**
     * 收尾模状态
     */
    public String statePre;

    /**
     * 检验批号
     */
    public String chkRmBn;

    /**
     * 送检数
     */
    public BigDecimal qtyCur;

    /**
     *
     */
    public BigDecimal qtyLost;

    public BigDecimal opRem;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date opDd;

    /**
     * 任务单号
     */
    public String jbNo;

    /**
     * 是否首模
     */
    public Boolean header;

    /**
     * 是否尾模
     */
    public Boolean tail;

    /**
     * 随工单明细表
     */
    public List<JmJobRecBDTO> jobRecB;

    /**
     * 设备号
     */
    public String rsNo;

}
