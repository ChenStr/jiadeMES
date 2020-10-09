package com.BSMES.jd.main.dto;
import com.BSMES.jd.common.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * 编码表映射
 */
@Data
public class InssysvarDTO extends BaseDTO implements Serializable {

    public Integer cCorp;

    /**
     * 变量名
     */
    public String sname;

    /**
     * 公式
     */
    public String sbds;

    /**
     * 参照值
     */
    public String sref;

    /**
     * 类型
     */
    public String chkflag;

    /**
     * 说明
     */
    public String remark;

    public String remarkxa;

    public String remarkxb;

    /**
     * 系统号
     */
    public Integer csysno;

    /**
     * 操作员
     */
    public String cuser;

    /**
     * 类别
     */
    public String stype;

    /**
     * 版本
     */
    public Integer cUlevel;

    public Integer ilevl;

    public String blast;

    public String bzLocked;

}
