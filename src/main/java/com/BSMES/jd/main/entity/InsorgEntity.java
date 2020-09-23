package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 部门映射表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("insorg")
public class InsorgEntity {

    private Integer cCorp;

    /**
     * 编码
     */
    @TableId
    private String orgcode;

    /**
     * 名称
     */
    private String orgname;

    private String orgnamexa;

    private String orgnamexb;

    /**
     * 级次
     */
    private Integer ilevl;

    /**
     * 末级
     */
    private String blast;

    /**
     * 公司
     */
    private String corg;

    /**
     * 终止日期
     */
    private Date eymd;

    /**
     * 属性
     */
    private Integer cattr;

    /**
     * 本位币
     */
    private String rmb;

}
