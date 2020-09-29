package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 仓库映射表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_wh")
public class JmWhEntity {

    /**
     * 仓库代号
     */
    @TableId
    public String wh;

    /**
     * 名称
     */
    public String name;

    /**
     * 属性
     */
    public String attrib;

    /**
     * 保管员
     */
    public String wkNo;

    /**
     * ERP仓库代号
     */
    public Integer whid;

}
