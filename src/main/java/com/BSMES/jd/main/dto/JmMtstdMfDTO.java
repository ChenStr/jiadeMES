package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.io.Serializable;

/**
 * 设备保养检点表头(维修保养项目)
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JmMtstdMfDTO extends BaseDTO implements Serializable {

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
