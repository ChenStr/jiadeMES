package com.BSMES.jd.main.dto;


import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * 来料检方案主表
 */
@Data
public class JmCheckPlanMfDTO extends BaseDTO {

    public String sid;

    public String name;

    public String checkType;

    public String prdNo;

    public String prdName;

    public String prdStd;

    public Date createTime;

    public String creator;

    public Date updateTime;

    public String updator;

}
