package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class JmxjAttach extends BaseDTO {

    public String sid;

    public String jbNo;

    public String prdNo;

    public String prdName;

    public String name;

    public String testBo;

    public String firstChk;

    public String ristSid;

    public String ristChk;

    public String smakeName;

    public Integer state;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date hpdate;

}
