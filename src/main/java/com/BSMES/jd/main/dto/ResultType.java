package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

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
     * 副单号
     */
    private Integer cid;

    /**
     * 其他单号
     */
    private String otherId;

    /**
     * 其他单号
     */
    private List<String> otherIds;


    /**
     * 车间名称
     */
    public String dep;

    /**
     * 车间代号
     */
    public String sorg;

    /**
     * 设备代号
     */
    public String devNo;

    /**
     * 设备名称
     */
    public String devName;

    /**
     * 设备类型
     */
    public String devid;

    /**
     * 模具代号
     */
    public String mouldNo;

    /**
     * 模具名称
     */
    public String mouldName;

    /**
     * 产品代号
     */
    public String prdNo;

    /**
     * 产品名称
     */
    public String prdName;

    /**
     * 状态
     */
    public Integer state;

    public List<Integer> otherState;

    /**
     * 类型
     */
    public String type;

    /**
     * 其他类型
     */
    public String otherType;

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

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date hpdate;

    /**
     * 不等于类型
     */
    public String notType;

    /**
     * 不等于部门
     */
    public String notSorg;

    /**
     * 人员代号
     */
    public String wkNo;

    /**
     * 人员姓名
     */
    public String wkName;

    public String moNo;

}
