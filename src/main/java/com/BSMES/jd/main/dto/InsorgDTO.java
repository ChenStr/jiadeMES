package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 部门映射表
 */
@Data
public class InsorgDTO extends BaseDTO implements Serializable {

    public Integer cCorp;

    /**
     * 编码
     */
    public String orgcode;

    /**
     * 名称
     */
    public String orgname;

    public String orgnamexa;

    public String orgnamexb;

    /**
     * 级次
     */
    public Integer ilevl;

    /**
     * 末级
     */
    public String blast;

    /**
     * 公司
     */
    public String corg;

    /**
     * 终止日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date eymd;

    /**
     * 属性
     */
    public Integer cattr;

    /**
     * 本位币
     */
    public String rmb;

    /**
     * 备注
     */
    public String rem;

    /**
     * 排序
     */
    public Integer sort;

}
