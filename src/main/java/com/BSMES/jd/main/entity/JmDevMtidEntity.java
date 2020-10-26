package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 设备维修项目
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_dev_mtid")
public class JmDevMtidEntity {

    /**
     * 设备代号
     */
    public String devNo;

    /**
     * 设备分类
     */
    @TableId
    public String devid;

    /**
     * 维修项目
     */
    @TableId
    public String mtId;

    /**
     * 项目信息
     */
    public String mtName;

    /**
     * 点检内容
     */
    public String mtRem;

    public String checkId;

    public String smake;

    public Date hpdate;

}
