package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class JmMtddMfDTO extends BaseDTO {



    public String sid;

    /**
     * 单据日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date hpdate;

    /**
     * 设备代号
     */
    public String devNo;

    /**
     * 单据状态
     */
    public Integer state;

    /**
     * 引用次数
     */
    public Integer creftimes;

    /**
     * 制单人
     */
    public String smake;

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
     * 备注
     */
    public String rem;

    /**
     * 业务类型
     */
    public String sbuid;

    /**
     * 部门
     */
    public String sorg;

    /**
     * 年月
     */
    public Integer chkym;

    /**
     * 设备分类
     */
    public String devid;

    /**
     * 计划单号
     */
    public String jbNo;

    /**
     * 开始时间
     */
    public Date begDd;

    /**
     * 结束时间
     */
    public Date endDd;

}
