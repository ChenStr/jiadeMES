package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 设备维修保养记录主表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_mt_rec")
public class JmMtRecEntity {

    /**
     * 维修代号
     */
    @TableId
    public String wxNo;

    /**
     * 录单日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date hpdate;

    /**
     * 维修人员
     */
    public String mainNo;

    /**
     * 始修日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date staDd;

    /**
     * 修复日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date finDd;

    /**
     * 维修项目
     */
    public String mtId;

    /**
     * 维修申请单单号
     */
    public String mtNo;

    /**
     * 序号
     */
    public Integer itm;

    /**
     * 设备代号
     */
    public String devNo;

    /**
     * 维修内容
     */
    public String prcRem;

    /**
     * 处理方式
     */
    public String prcId;

    /**
     * 申请人
     */
    public String salNo;

    /**
     * 申请部门
     */
    public String dep;

    /**
     * 检修前存在的问题
     */
    public String rem;

    /**
     * 任务单号
     */
    public String jbNo;

    /**
     * 制单人
     */
    public String smake;

    /**
     * 单据类型
     */
    public String sbuid;

    /**
     * 资源代号
     */
    public String rsNo;

    /**
     * 部门
     */
    public String sorg;

    /**
     * 设备类型代号
     */
    public String devid;

    /**
     * 设备类型名称
     */
    public String devidname;

    /**
     * 保修类型
     */
    public Integer mtType;

    /**
     * 维修状态
     */
    public Integer state;

    /**
     * 维修后的结论
     */
    public String prcRlt;

    /**
     * 备注
     */
    public String rltRem;

    /**
     * 设备名称
     */
    public String devName;
}
