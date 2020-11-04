package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 模具报废子表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_mdbf_tf")
public class JmMdbfTfEntity {

    /**
     * 单据编码
     */
    @TableId
    public String sid;

    /**
     * 项次
     */
    @TableId
    public Integer cid;

    /**
     * 工装代号
     */
    public String mdNo;

    /**
     * 仓库
     */
    public String wh;

    /**
     * 储位
     */
    public String whLoc;

    /**
     * 备注
     */
    public String rem;

    /**
     * 单据日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date hpdate;

    /**
     * 锁定标志
     */
    public Integer edLocked;

    /**
     * 工装类型
     */
    public Integer typeid;

    /**
     * 模具名称
     */
    public String mdName;

}
