package com.BSMES.jd.main.dto;
import com.BSMES.jd.common.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * 编码表映射
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InssysvarDTO extends BaseDTO implements Serializable {

    private Integer cCorp;

    /**
     * 变量名
     */
    private String sname;

    /**
     * 公式
     */
    private String sbds;

    /**
     * 参照值
     */
    private String sref;

    /**
     * 类型
     */
    private String chkflag;

    /**
     * 说明
     */
    private String remark;

    private String remarkxa;

    private String remarkxb;

    /**
     * 系统号
     */
    private Integer csysno;

    /**
     * 操作员
     */
    private String cuser;

    /**
     * 类别
     */
    private String stype;

    /**
     * 版本
     */
    private Integer cUlevel;

    private Integer ilevl;

    private String blast;

    private String bzLocked;

}
