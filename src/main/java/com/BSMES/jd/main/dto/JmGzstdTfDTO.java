package com.BSMES.jd.main.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 工装保养维修计划(子表)
 */
@Data
public class JmGzstdTfDTO {

    /**
     * 检测标准编码
     */
    public String gzstdNo;

    /**
     * 项次
     */
    public Integer cid;

    /**
     * 维修项目
     */
    public String wxId;

    /**
     * 判定标准
     */
    public String chkStd;

    /**
     * 模具部位
     */
    public String devps;

    /**
     * 检测方法
     */
    public String chkRem;

    /**
     * 链接文件
     */
    public String chkFile;

    /**
     * 说明
     */
    public String rem;

}
