package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 设备(加工资源)
 */
@Data
public class JmDevDTO extends BaseDTO implements Serializable {

    /**
     * 设备代号
     */
    public String devNo;

    /**
     * 名称
     */
    public String name;

    /**
     * 型号
     */
    public String spc;

    /**
     * 关联资源
     */
    public String rsNo;

    /**
     * 供应厂商
     */
    public String cusname;

    /**
     * 维护人员
     */
    public String mainNo;

    /**
     * 部门
     */
    public String dep;

    /**
     * 时间上限
     */
    public BigDecimal maxtime;

    /**
     * 数量上限
     */
    public BigDecimal maxqty;

    /**
     * 累计加工时间
     */
    public BigDecimal timeMk;

    /**
     * 累计加工数量
     */
    public BigDecimal qtyMk;

    /**
     * 状态
     */
    public String state;

    /**
     * 设备分类
     */
    public String devid;

    /**
     * 部门
     */
    public String sorg;

    /**
     * 图片
     */
    public String picture;

    /**
     * 位置
     */
    public String location;

    /**
     * IP地址
     */
    public String ip;

    /**
     * 部门名称
     */
    public String depName;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date hpdate;

    /**
     * 工序代号
     */
    public String zcNo;

    /**
     * 出厂日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public String staDd;

    /**
     * 模糊查询的devNo
     */
    public String dev_no;

}
