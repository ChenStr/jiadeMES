package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 检验标准主表
 */
@Data
public class JmChkstdTfDTO extends BaseDTO {

    /**
     * 检验标准编码
     */
    public String chkstdNo;

    /**
     * 项次
     */
    public Integer cid;

    /**
     * 检验项目
     */
    public String chkNo;

    /**
     * 检验标准
     */
    public String chkPara;

    /**
     * 下限
     */
    public BigDecimal paraMin;

    /**
     * 上限
     */
    public BigDecimal paraMax;

    /**
     * 单位
     */
    public String paraUnit;

    /**
     * 检验工具
     */
    public String tlNo;

    /**
     * 检验方法
     */
    public String chkRem;

    /**
     * 链接方法
     */
    public String chkFile;

    /**
     * 说明
     */
    public String rem;

    /**
     * 伟高测试关联项
     */
    public String tsNo;

    /**
     * 检验类型
     */
    public String chkId;

}
