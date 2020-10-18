package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 检验标准主表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_chkstd_mf")
public class JmChkstdMfEntity {

    /**
     * 检验标准编码
     */
    public String chkstdNo;

    /**
     * 检验标准名称
     */
    public String name;

    /**
     * 检验标识
     */
    public String xjId;

    /**
     * 检验方式
     */
    public Integer chkid;

    /**
     * 检验标准
     */
    public String chkClass;

    /**
     * 货品代号
     */
    public String prdNo;

    /**
     * 工序
     */
    public String zcNo;

    /**
     * 等级设置
     */
    public String prdGrade;

    /**
     * 说明
     */
    public String rem;

    /**
     * 巡检模式
     */
    public String xjms;

    /**
     * 部门
     */
    public String sorg;

    /**
     * 货品代号
     */
    public String prdName;

    /**
     * 部门名称
     */
    public String sorgName;
}
