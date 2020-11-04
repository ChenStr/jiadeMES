package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 模具表
 */
@Data
public class JmMouldDTO extends BaseDTO implements Serializable {

    /**
     * 模具代号
     */
    @TableId
    public String mdNo;

    /**
     * 工装类型
     */
    @TableId
    public Integer typeid;

    /**
     * 名称
     */
    public String name;

    /**
     * 数量
     */
    public BigDecimal mdQty;

    /**
     * 模具分类
     */
    public String mdGrp;

    /**
     * 状态
     */
    public String state;

    /**
     * 仓库
     */
    public String wh;

    /**
     * 储位
     */
    public String whLoc;

    /**
     * 寿命次数
     */
    public BigDecimal qtyMax;

    /**
     * 累计使用次数
     */
    public BigDecimal qtyMk;

    /**
     * 寿命时间
     */
    public BigDecimal timeMax;

    /**
     * 累计加工时间
     */
    public BigDecimal timeMk;

    /**
     * 领用部门
     */
    public String dep;

    /**
     * 责任人
     */
    public String pressName;

    /**
     * 维修人
     */
    public String wxName;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date hpdate;

    /**
     * 制单人员
     */
    public String smake;

    /**
     * 制作日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date creDd;

    /**
     * 确认人
     */
    public String confirName;

    /**
     * 使用单位
     */
    public String userunit;

    /**
     * 责任人代号
     */
    public String pressNo;

    /**
     * 确认人代号
     */
    public String confirNo;

    /**
     * 维修人代号
     */
    public String wxNo;

}
