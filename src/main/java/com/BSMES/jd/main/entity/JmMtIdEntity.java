package com.BSMES.jd.main.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 检点项目(维修保养项目)
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_mt_id")
public class JmMtIdEntity {

    /**
     * 项目代号
     */
    @TableId
    public String mtId;

    /**
     * 名称
     */
    public String name;



}
