package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * 设备维护人员表
 */
@Data
public class JmDevSalDTO extends BaseDTO {

    /**
     * 设备代号
     */
    public String devNo;

    /**
     * 维护人员代号
     */
    public String salNo;

    /**
     * 部门代号
     */
    public String dep;

    /**
     * 部门名称
     */
    public String depName;

    /**
     * 设备名称
     */
    public String devName;

    /**
     * 创建时间
     */
    public Date hpdate;

    /**
     * 维护人员姓名
     */
    public String salName;

}
