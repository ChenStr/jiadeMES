package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 设备点检子表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_mtdd2_tf")
public class JmMtdd2TfEntity {

    /**
     * 检测标准编码
     */
    @TableId
    public String sid;

    /**
     * 项次
     */
    @TableId
    public Integer cid;

    /**
     * 点检部位
     */
    public String devps;

    /**
     * 点检内容
     */
    public String mtRem;

    /**
     * 操作员
     */
    public String wkNo;

    /**
     * 备注
     */
    public String rem;

    /**
     * 点检项目
     */
    public String mtId;

    /**
     * 判定标准
     */
    public String chkStd;

    /**
     * 点检名称
     */
    public String mtName;

    /**
     * 点检日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date hpdate;

    /**
     * 操作员名称
     */
    public String wkName;

}
