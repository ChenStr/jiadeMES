package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 岗位定义
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("insgwcode")
public class InsgwcodeEntity {

    /**
     * 集团
     */
    public Integer cCorp;

    /**
     * 编码
     */
    @TableId
    public String gwcode;

    /**
     * 名称
     */
    public String gwname;

    /**
     * 说明
     */
    public String smemo;

}
