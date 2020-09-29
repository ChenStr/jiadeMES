package com.BSMES.jd.main.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * bom表表头映射
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_bom_mf")
public class JmBomMfEntity {

    /**
     * bom代号
     */
    @TableId
    public String bomNo;

    public String sbuid;

    /**
     * 单据日期
     */
    public Date hpdate;

    public String pfNo;

    /**
     * 产品代号
     */
    public String prdNo;

    public String name;

    public String dep;

    public String wh;

    public String unit;

    public BigDecimal qty;

    public BigDecimal usedTime;

    public BigDecimal timeCnt;

    public BigDecimal cst;

    public String rem;

    public Integer bomid;

    public String unitErp;

    public Integer state;

    public String smake;

    public Integer creftimes;

    public String sorg;

    public Integer parentid;

    public String prdMark;

    public Integer chkId;

}
