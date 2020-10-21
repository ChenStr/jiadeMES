package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * 设备维护人员表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_dev_sal")
public class JmDevSalEntity {

    /**
     * 设备代号
     */
    @TableId
    public String devNo;

    /**
     * 维护人员代号
     */
    @TableId
    public String salNo;

    /**
     * 部门代号
     */
    public String dep;

    /**
     * 部门名称
     */
    public String depName;

    /**
     * 设备名称
     */
    public String devName;

    /**
     * 创建时间
     */
    public Date hpdate;

    /**
     * 维护人员姓名
     */
    public String salName;

}
