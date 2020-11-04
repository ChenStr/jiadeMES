package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 模具还回子表
 */
@Data
public class JmMdbkTfDTO extends BaseDTO {

    /**
     * 单据代号
     */
    public String sid;

    /**
     * 项次
     */
    public Integer cid;

    /**
     * 工装代号
     */
    public String mdNo;

    /**
     * 仓库
     */
    public String wh;

    /**
     * 储位
     */
    public String whLoc;

    /**
     * 备注
     */
    public String rem;

    /**
     * 单据日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date hpdate;

    /**
     * 锁定标志
     */
    public Integer edLocked;

    /**
     * 工装类型
     */
    public Integer typeid;

    /**
     * 生产数量
     */
    public BigDecimal qtyProduce;

    /**
     * 生产模数
     */
    public BigDecimal qtyMould;

    /**
     * 修复履历
     */
    public String remWx;

    /**
     * 使用次数
     */
    public BigDecimal qtyMk;

    /**
     * 模具名称
     */
    public String mdName;

}
