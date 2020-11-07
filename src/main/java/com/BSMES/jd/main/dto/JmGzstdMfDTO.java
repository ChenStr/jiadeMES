package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 工装保养维修计划(主表)
 */
@Data
public class JmGzstdMfDTO extends BaseDTO {

    /**
     * 检测标准编码
     */
    public String gzstdNo;

    /**
     * 检测标准名称
     */
    public String name;

    /**
     * 保养级别
     */
    public Integer mdClass;

    /**
     * 模具代号
     */
    public String mdNo;

    /**
     * 模具分类
     */
    public String mdGrp;

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
    public Integer state;

    /**
     * 部门
     */
    public String dep;

    /**
     * 部门名称
     */
    public String depName;

    /**
     * 保养周期
     */
    public Integer byzqDd;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date scbyDd;

}
