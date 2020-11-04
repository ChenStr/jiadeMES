package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工装维修保养项目设定
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_wx_id")
public class JmWxIdEntity {

    /**
     * 项目代号
     */
    @TableId
    public String mtId;

    /**
     * 名称
     */
    public String name;

    /**
     * 分类
     */
    public String mdGrp;

    /**
     * 部门
     */
    public String dep;

    /**
     * 部门名称
     */
    public String depName;

}
