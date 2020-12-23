package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 来料检子表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_goods_check_tf")
public class JmGoodsCheckTfEntity {

    @TableId
    public String sid;

    @TableId
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
