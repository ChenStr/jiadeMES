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
public class jmWhEntity {

    /**
     * 仓库代号
     */
    @TableId
    private String wh;

    /**
     * 名称
     */
    private String name;

    /**
     * 属性
     */
    private String attrib;

    /**
     * 保管员
     */
    private String wkNo;

    /**
     * ERP仓库代号
     */
    private Integer whid;

}
