package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 公共条件类
 */
@Data
public class ResultType extends BaseDTO {

    /**
     * 单号
     */
    private String sid;


    /**
     * 车间名称
     */
    public String dep;

    /**
     * 设备名称
     */
    public String devName;

    /**
     * 模具名称
     */
    public String mouldName;

    /**
     * 产品名称
     */
    public String prdName;

    /**
     * 状态
     */
    public Integer state;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date begDd;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date endDd;

}
