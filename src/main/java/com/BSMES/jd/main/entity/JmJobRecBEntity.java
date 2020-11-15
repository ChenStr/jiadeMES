package com.BSMES.jd.main.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 随工单明细
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_job_rec_b")
public class JmJobRecBEntity {

    @TableId
    public String opsid;

    @TableId
    public Integer cid;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    public Date opDd;

    public String sn;

    public Integer dmdel;

    /**
     * 作业人员
     */
    public String wkNo;

    /**
     *
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

    /**
     * 不合格原因
     */
    public String spcNo;

    /**
     * 重量
     */
    public BigDecimal qtyWt;

    /**
     * 工序
     */
    public String zcNo;

    /**
     * 制单人
     */
    public String smake;

}
