package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单定义
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("insmenu")
public class InsmenuEntity {

    /**
     * 集团
     */
    public Integer cCorp;

    /**
     * 编号
     */
    @TableId
    public String menuid;

    /**
     * 名称
     */
    public String menuname;

    /**
     * 属性
     */
    public Integer menuattr;

    /**
     * 终端类别
     */
    public Integer mbtype;

    /**
     * 版本
     */
    @TableId
    public Integer cUlevel;

}
