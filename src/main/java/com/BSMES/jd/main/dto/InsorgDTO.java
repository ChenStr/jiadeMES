package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 部门映射表
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    public Date eymd;

    /**
     * 属性
     */
    public Integer cattr;

    /**
     * 本位币
     */
    public String rmb;

}
