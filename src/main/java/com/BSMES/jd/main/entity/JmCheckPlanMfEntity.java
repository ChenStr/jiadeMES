package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 来料检方案主表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_check_plan_mf")
public class JmCheckPlanMfEntity {

    @TableId
    public String sid;

    public String name;

    public String checkType;

    public String prdNo;

    public String prdName;

    public String prdStd;

    public Date createTime;

    public String creator;

    public Date updateTime;

    public String updator;

}
