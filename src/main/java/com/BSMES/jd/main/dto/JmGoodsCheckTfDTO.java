package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 来料检子表
 */
@Data
public class JmGoodsCheckTfDTO extends BaseDTO {

    public String sid;

    public Integer cid;

    public String itemSid;

    public String itemName;

    public String skill;

    public String testingData;

    public String testToDecide;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date createTime;

    public String creator;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date updateTime;

    public String updator;

    public String rem;

}
