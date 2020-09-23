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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JmWorkerDTO extends BaseDTO implements Serializable {

    /**
     * 人员代号
     */
    private String wkNo;

    /**
     * 名称
     */
    private String name;

    /**
     * 数量
     */
    private BigDecimal wkQty;

    /**
     * 技能等级
     */
    private String wkGrp;

    /**
     * 状态
     */
    private String state;

    /**
     * 部门
     */
    private String dep;

    /**
     * 操作员(与Insuser相关联，多个的话使用 ; 分开)
     */
    private String usrRs;

    /**
     * 部门
     */
    private String sorg;

    private String wsNo;

    /**
     * Linker用户号
     */
    private String lnkusr;

    /**
     * Linker用户名
     */
    private String lnkname;

    /**
     * Linker公司号
     */
    private String lnkcompid;

    /**
     * Linker公司电话
     */
    private String lnktel;

    /**
     * Linker令牌
     */
    private String lnktoken;

    /**
     * 图片
     */
    private String picture;

    /**
     * 加工中心
     */
    private String rsGrp;

    /**
     * 加工资源
     */
    private String rsNo;

}
