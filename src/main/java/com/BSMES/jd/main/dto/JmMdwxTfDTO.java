package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class JmMdwxTfDTO extends BaseDTO {

    /**
     * 单号
     */
    public String sid;

    /**
     * 项次
     */
    public Integer cid;

    /**
     * 货品名称
     */
    public String jbNo;

    /**
     * 货品代号
     */
    public String prdNo;

    /**
     * 货品名称
     */
    public String prdName;

    /**
     * 发现时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date findDd;

    /**
     * 操作者
     */
    public String wkNo;

    /**
     * 保养项目
     */
    public String mtId;

    /**
     * 备注
     */
    public String rem;

    /**
     * 填报者
     */
    public String wkName;

    /**
     * 检验结果
     */
    public String curPara;

    public String byNo;

    public String byName;

}
