package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 模具还回主表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_mdbk_mf")
public class JmMdbkMfEntity {

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
     * 单据日期
     */
    public Date hpdate;

    /**
     * 部门
     */
    public String dep;

    /**
     * 仓库
     */
    public String wh;

    /**
     * 制单人
     */
    public String smake;

    /**
     * 更新时间
     */
    public Date moditime;

    /**
     * 审核人
     */
    public String chkMan;

    /**
     * 状态
     */
    public Integer state;

    /**
     * 引用次数
     */
    public Integer creftimes;

    /**
     * 部门
     */
    public String sorg;

    /**
     * 备注
     */
    public String rem;

    /**
     * 锁定标志
     */
    public Integer edLocked;

    /**
     * 工装类型
     */
    public Integer typeid;

    /**
     * 计划单号
     */
    public String jbNo;

}
