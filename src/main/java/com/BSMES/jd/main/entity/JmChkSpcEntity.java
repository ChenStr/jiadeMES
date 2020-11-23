package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 检验不合格原因设定
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_chk_spc")
public class JmChkSpcEntity {

    @TableId
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
