package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 修模维修单主表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_mdwx")
public class JmMdwxEntity {

    /**
     * 单据编码
     */
    @TableId
    public String sid;

    /**
     * 业务类型
     */
    public String sbuid;

    /**
     * 下模时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date hpdate;

    /**
     * 部门
     */
    public String dep;

    /**
     * 维修人员
     */
    public String wkNo;

    /**
     * 制单人员
     */
    public String smake;

    /**
     * 模具编号
     */
    public String mdNo;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date staDd;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date finDd;

    /**
     * 维修说明
     */
    public String prcRem;

    /**
     * 处理方式
     */
    public String prcId;

    /**
     * 备注
     */
    public String rem;

    /**
     * 锁定标志
     */
    public Integer edLocked;

    /**
     * 工装状态
     */
    public Integer state;

    /**
     * 工装类型
     */
    public Integer typeid;

}
