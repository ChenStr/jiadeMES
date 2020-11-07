package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工装保养维修计划(子表)
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_gzstd_tf")
public class JmGzstdTfEntity {

    /**
     * 检测标准编码
     */
    @TableId
    public String gzstdNo;

    /**
     * 项次
     */
    @TableId
    public Integer cid;

    /**
     * 维修项目
     */
    public String wxId;

    /**
     * 判定标准
     */
    public String chkStd;

    /**
     * 模具部位
     */
    public String devps;

    /**
     * 检测方法
     */
    public String chkRem;

    /**
     * 链接文件
     */
    public String chkFile;

    /**
     * 说明
     */
    public String rem;

}
