package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 岗位菜单中间表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("inslimit")
public class InslimitEntity {

    /**
     * 集团
     */
    public Integer cCorp;

    /**
     * 岗位人员码
     */
    @TableId
    public String gwuser;

    /**
     * 菜单号
     */
    @TableId
    public String menuid;

    /**
     * 岗位
     */
    @TableId
    public String cgw;

}
