package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 修模维修单子表原因
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_mdwx_tf")
public class JmMdwxTfEntity {

    /**
     * 单号
     */
    @TableId
    public String sid;

    /**
     * 项次
     */
    @TableId
    public Integer cid;

    /**
     * 货品名称
     */
    public String jbNo;

    /**
     * 货品代号
     */
    public String prdNo;

    /**
     * 货品名称
     */
    public String prdName;

    /**
     * 发现时间
     */
    public Date findDd;

    /**
     * 操作者
     */
    public String wkNo;

    /**
     * 保养项目
     */
    public String mtId;

    /**
     * 备注
     */
    public String rem;

    /**
     * 填报者
     */
    public String wkName;


}
