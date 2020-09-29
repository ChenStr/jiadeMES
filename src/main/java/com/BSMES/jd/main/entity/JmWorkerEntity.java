package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 人员映射表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_worker")
public class JmWorkerEntity {

    /**
     * 反序列化规则
     */
//    public static final long serialVersionUID = 1L;

    /**
     * 人员代号
     */
    @TableId
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
