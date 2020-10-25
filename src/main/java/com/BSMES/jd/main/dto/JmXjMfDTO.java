package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 巡检单主表映射表
 */
@Data
public class JmXjMfDTO extends BaseDTO implements Serializable {

    /**
     * 单据代号
     */
    public String sid;

    /**
     * 单据日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date hpdate;

    /**
     * 业务类型
     */
    public String sbuid;

    /**
     * 检验人员
     */
    public String smake;

    /**
     * 更新时间
     */
    public Date moditime;

    /**
     * 审核人
     */
    public String chkMan;

    /**
     * 状态
     */
    public Integer state;

    /**
     * 引用次数
     */
    public Integer creftimes;

    /**
     * 加工资源
     */
    public String rsNo;

    /**
     * 调度单号
     */
    public String moNo;

    /**
     * 产品代号
     */
    public String prdNo;

    /**
     * 批号
     */
    public String batNo;

    /**
     * 工序代号
     */
    public String zcNo;

    /**
     * 检验标识
     */
    public String xjid;

    /**
     * 操作人员
     */
    public String wkNo;

    /**
     * 部门
     */
    public String sorg;

    /**
     * 箱码
     */
    public String zxCode;

    /**
     * 报工数量
     */
    public BigDecimal qtyCur;

    /**
     * 合格量
     */
    public BigDecimal qtyOk;

    /**
     * 不合格量
     */
    public BigDecimal qtyLost;

    /**
     * 不合格原因
     */
    public String spcChk;

    /**
     * 不合格原因及数量
     */
    public String spcBtn;

    /**
     * 处理意见
     */
    public String chkId;

    /**
     * 备注
     */
    public String opRem;

    /**
     * 处理结果
     */
    public String finJudge;

    /**
     * 细节描述
     */
    public String xjms;

    /**
     * 细节检定
     */
    public String xjjd;

    /**
     * 检验依据
     */
    public String testBo;

    /**
     * 模具代号
     */
    public String mdNo;

    /**
     * 首件确认
     */
    public String firstChk;

    /**
     * 外观
     */
    public String aspectChk;

    /**
     * 互换性
     */
    public String interChk;

    /**
     * 可焊性
     */
    public String welcChk;

    /**
     * 计划单号
     */
    public String jbNo;

    /**
     * 锡青铜带
     */
    public String qcopper;

    /**
     * 黄铜带
     */
    public String hcopper;

    /**
     * 塑料粒子
     */
    public String plastic;

    /**
     * 黄铜针
     */
    public String zcopper;

    /**
     * ROHS
     */
    public String rohs;

    /**
     * 工艺确认
     */
    public String techno;

    /**
     * 检验项目描述
     */
    public String chkRem;

    /**
     * 原料名称
     */
    public String rmName;

    /**
     * 操作员名称
     */
    public String wkName;

    /**
     * 制单人名称
     */
    public String smakeName;

    /**
     * 产品名称
     */
    public String prdName;
}
