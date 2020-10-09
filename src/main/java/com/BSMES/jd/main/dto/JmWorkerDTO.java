package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 人员映射表
 */
@Data
public class JmWorkerDTO extends BaseDTO implements Serializable {

    /**
     * 人员代号
     */
    public String wkNo;

    /**
     * 名称
     */
    public String name;

    /**
     * 数量
     */
    public BigDecimal wkQty;

    /**
     * 技能等级
     */
    public String wkGrp;

    /**
     * 状态
     */
    public String state;

    /**
     * 部门
     */
    public String dep;

    /**
     * 操作员(与Insuser相关联，多个的话使用 ; 分开)
     */
    public String usrRs;

    /**
     * 部门
     */
    public String sorg;

    public String wsNo;

    /**
     * Linker用户号
     */
    public String lnkusr;

    /**
     * Linker用户名
     */
    public String lnkname;

    /**
     * Linker公司号
     */
    public String lnkcompid;

    /**
     * Linker公司电话
     */
    public String lnktel;

    /**
     * Linker令牌
     */
    public String lnktoken;

    /**
     * 图片
     */
    public String picture;

    /**
     * 加工中心
     */
    public String rsGrp;

    /**
     * 加工资源
     */
    public String rsNo;

}
