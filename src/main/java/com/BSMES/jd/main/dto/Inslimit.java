package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import lombok.Data;

@Data
public class Inslimit extends BaseDTO {

    public String menuid;

    public String menuname;

    public String menuicon;

    public String browserUrl;

    public String sremark;

    public Integer cgw;

    public Integer mbtype;

}
