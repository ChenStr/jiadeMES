package com.BSMES.jd.main.dto;
import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 不合格原因表
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JmXj1TfDTO extends BaseDTO implements Serializable {

    /**
     * 单据代号
     */
    public String sid;

    /**
     * 项次
     */
    public Integer cid;

    /**
     * 不合格原因及代号
     */
    public String spcNo;

    /**
     * 不合格数量
     */
    public BigDecimal qty;

}
