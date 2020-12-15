package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 来料检方案子表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_check_plan_tf")
public class JmCheckPlanTfEntity {

    @TableId
    public String sid;

    @TableId
    public Integer cid;

    public String skill;

    public String itemId;

    public String itemName;

    public String mode;

    public String rem;

    public Date createTime;

    public String creator;

    public Date updateTime;

    public String updator;

}
