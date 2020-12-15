package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 来料检方案子表
 */
@Data
public class JmCheckPlanTfDTO extends BaseDTO {

    public String sid;

    public Integer cid;

    public String skill;

    public String itemId;

    public String itemName;

    public String mode;

    public String rem;

    public Date createTime;

    public String creator;

    public Date updateTime;

    public String updator;

}
