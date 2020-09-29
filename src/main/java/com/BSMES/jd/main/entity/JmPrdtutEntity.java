package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 单位映射表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_prdtut")
public class JmPrdtutEntity {

    /**
     * 单位
     */
    public String ubm;

    /**
     * 单位名称
     */
    public String umc;

}
