package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * bom表表头映射
 */
@Data
public class JmBomMfDTO extends BaseDTO implements Serializable {

    /**
     * bom代号
     */
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
