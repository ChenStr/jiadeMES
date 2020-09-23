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

    private Integer cCorp;

    /**
     * 编码
     */
    private String orgcode;

    /**
     * 名称
     */
    private String orgname;

    private String orgnamexa;

    private String orgnamexb;

    /**
     * 级次
     */
    private Integer ilevl;

    /**
     * 末级
     */
    private String blast;

    /**
     * 公司
     */
    private String corg;

    /**
     * 终止日期
     */
    private Date eymd;

    /**
     * 属性
     */
    private Integer cattr;

    /**
     * 本位币
     */
    private String rmb;

}
