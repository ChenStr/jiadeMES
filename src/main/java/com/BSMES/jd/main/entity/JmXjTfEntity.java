package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 巡检单子表映射表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_xj_tf")
public class JmXjTfEntity {
    /**
     * 单据代号
     */
    @TableId
    public String sid;

    /**
     * 项次
     */
    @TableId
    public Integer cid;

    /**
     * 检验项目
     */
    public String chkNo;

    /**
     * 检验标准
     */
    public String chkPara;

    /**
     * 方法
     */
    public String chkRem;

    /**
     * 结果
     */
    public String rtXj;

    /**
     * 备注
     */
    public String rem;

    /**
     * 检验类型
     */
    public Integer chkId;

    /**
     * 检验值1
     */
    public String curPara1;

    /**
     * 检验值2
     */
    public String curPara2;

    /**
     * 检验值3
     */
    public String curPara3;

    /**
     * 检验值4
     */
    public String curPara4;

    /**
     * 检验值5
     */
    public String curPara5;

    /**
     * 检验值6
     */
    public String curPara6;
}
