package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.internal.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备保养检点表身(维修保养项目)
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JmMtstdTfDTO extends BaseDTO implements Serializable {

    /**
     * 单据代号
     */
    public String mtstdNo;

    /**
     * 项次
     */
    public Integer cid;

    /**
     * 检点项目
     */
    public String mtId;

    /**
     * 判定标准
     */
    public String chkStd;

    /**
     * 检点部位
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

    /**
     * 检点日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date createDate;

    /**
     * 检点人
     */
    public String creator;

}
