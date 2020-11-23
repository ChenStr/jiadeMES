package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 检验不合格原因设定
 */
@Data
public class JmChkSpcDTO extends BaseDTO {

    public String spcChk;

    public String name;

    public String spcType;

    public String chkNo;

    public Integer blist;

    public String prdNo;

    public String zcNo;

    public String rem;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date hpdate;

    public String smake;

}
