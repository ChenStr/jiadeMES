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

    public Integer cCorp;

    /**
     * 编码
     */
    @TableId
    public String orgcode;

    /**
     * 名称
     */
    public String orgname;

    public String orgnamexa;

    public String orgnamexb;

    /**
     * 级次
     */
    public Integer ilevl;

    /**
     * 末级
     */
    public String blast;

    /**
     * 公司
     */
    public String corg;

    /**
     * 终止日期
     */
    public Date eymd;

    /**
     * 属性
     */
    public Integer cattr;

    /**
     * 本位币
     */
    public String rmb;

}
