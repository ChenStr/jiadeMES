package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 来料检检验项目
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_check_item")
public class JmCheckItemEntity {

    @TableId
    public String sid;

    public String name;

    public String checkType;

    public String mode;

    public String rem;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date createTime;

    public String creator;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date updateTime;

    public String updator;

}
