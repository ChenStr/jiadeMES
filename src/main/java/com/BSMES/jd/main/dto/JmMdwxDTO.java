package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 修模维修单主表
 */
@Data
public class JmMdwxDTO extends BaseDTO {

    /**
     * 单据编码
     */
    public String sid;

    /**
     * 业务类型
     */
    public String sbuid;

    /**
     * 下模时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date hpdate;

    /**
     * 部门
     */
    public String dep;

    /**
     * 维修人员
     */
    public String wkNo;

    /**
     * 制单人员
     */
    public String smake;

    /**
     * 模具编号
     */
    public String mdNo;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date staDd;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date finDd;

    /**
     * 维修说明
     */
    public String prcRem;

    /**
     * 处理方式
     */
    public String prcId;

    /**
     * 备注
     */
    public String rem;

    /**
     * 锁定标志
     */
    public Integer edLocked;

    /**
     * 工装状态
     */
    public Integer state;

    /**
     * 工装类型
     */
    public Integer typeid;

    /**
     * 下模时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date lowerDd;

    /**
     * 维修内容
     */
    public String wxRem;

    /**
     * 报修类型
     */
    public String mdType;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date moditime;

    /**
     * 审核人
     */
    public String chkMan;

    /**
     * 库位名称
     */
    public String whlocName;

    /**
     * 库位代号
     */
    public String whloc;

    /**
     * 审核人名称
     */
    public String chkmanName;

    /**
     * 维修工名称
     */
    public String wkName;

    /**
     * 制单人员名称
     */
    public String smakeName;

}
