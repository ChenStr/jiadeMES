package com.BSMES.jd.main.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 模具领用子表
 */
@Data
public class JmMdlyTfDTO {

    /**
     * 单据编码
     */
    public String sid;

    /**
     * 项次
     */
    public Integer cid;

    /**
     * 工装代号
     */
    public String mdNo;

    /**
     * 仓库
     */
    public String wh;

    /**
     * 储位
     */
    public String whLoc;

    /**
     * 制令单号
     */
    public String moNo;

    /**
     * 备注
     */
    public String rem;

    /**
     * 单据日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date hpdate;

    /**
     * 锁定标志
     */
    public Integer edLocked;

    /**
     * 工装类型
     */
    public Integer typeid;

    /**
     * 领用人
     */
    public String mdlyNo;

    /**
     * 领出日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date mdlyDd;

    /**
     * 模具名称
     */
    public String mdName;

    /**
     * 计划单号
     */
    public String jbNo;

}
