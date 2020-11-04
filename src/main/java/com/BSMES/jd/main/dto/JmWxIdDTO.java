package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import lombok.Data;

@Data
public class JmWxIdDTO extends BaseDTO {

    /**
     * 项目代号
     */
    public String mtId;

    /**
     * 名称
     */
    public String name;

    /**
     * 分类
     */
    public String mdGrp;

    /**
     * 部门
     */
    public String dep;

    /**
     * 部门名称
     */
    public String depName;
}
