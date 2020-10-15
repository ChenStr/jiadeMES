package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * 设备点检子表
 */
@Data
public class JmMtdd2TfDTO extends BaseDTO {

    /**
     * 检测标准编码
     */
    @TableId
    public String sid;

    /**
     * 项次
     */
    @TableId
    public Integer cid;

    /**
     * 点检部位
     */
    public String devps;

    /**
     * 点检内容
     */
    public String mtRem;

    /**
     * 操作员
     */
    public String wkNo;

    /**
     * 备注
     */
    public String rem;

    /**
     * 点检项目
     */
    public String mtId;

    /**
     * 判定标准
     */
    public String chkStd;

    /**
     * 点检名称
     */
    public String mtName;

    /**
     * 点检日期
     */
    public Date hpdate;

}
