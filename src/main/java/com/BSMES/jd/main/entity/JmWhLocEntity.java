package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 仓库映射表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_wh_loc")
public class JmWhLocEntity {

    /**
     * 储位代号
     */
    @TableId
    public String whLoc;

    /**
     * 名称
     */
    public String name;

    /**
     * 仓库
     */
    public String wh;

    /**
     * 备注
     */
    public String rem;

    /**
     * 制单人
     */
    public String smake;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date hpdate;

    /**
     * 排序
     */
    public Integer sort;

}
