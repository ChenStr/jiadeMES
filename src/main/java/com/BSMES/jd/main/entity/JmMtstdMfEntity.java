package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 设备保养检点表头(维修保养项目)
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_mtstd_mf")
public class JmMtstdMfEntity {

    /**
     * 单据代号
     */
    @TableId
    public String mtstdNo;

    /**
     * 检测标准名称
     */
    public String name;

    /**
     * 保养级别
     */
    public Integer mtClass;

    /**
     * 设备代号
     */
    public String devNo;

    /**
     * 设备分类
     */
    public String devid;

    /**
     * 说明
     */
    public String rem;

    /**
     * 单据类型
     */
    public String sbuid;

    /**
     * 状态
     */
    public String state;

}
