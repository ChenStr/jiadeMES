package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 随工单明细
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JmJobRecBDTO extends BaseDTO implements Serializable {

    public String opsid;

    public Integer cid;

    public Date opDd;

    public String sn;

    public Integer dmdel;

    /**
     * 作业人员
     */
    public String wkNo;

    /**
     * 重量
     */
    public BigDecimal qty;

    /**
     * 合格数
     */
    public BigDecimal qtyOk;

    /**
     * 不合格数
     */
    public BigDecimal qtyLost;

    /**
     * 检验产品批号
     */
    public String chkRmBn;

    /**
     * 检验人员
     */
    public String chkNo;

    /**
     * 工序
     */
    public String process;

}
