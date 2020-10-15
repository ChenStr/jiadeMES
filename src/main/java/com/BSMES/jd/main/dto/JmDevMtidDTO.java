package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import lombok.Data;

@Data
public class JmDevMtidDTO extends BaseDTO {

    /**
     * 设备代号
     */
    public String devNo;

    /**
     * 设备分类
     */
    public String devid;

    /**
     * 维修项目
     */
    public String mtId;

    /**
     * 项目信息
     */
    public String mtName;

    /**
     * 点检内容
     */
    public String mtRem;

}
