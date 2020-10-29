package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 模具报废子表
 */
@Data
public class JmMdbfTfDTO extends BaseDTO {

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
     * 单据日期
     */
    public Date hpdate;

    /**
     * 锁定标志
     */
    public Integer edLocked;

    /**
     * 工装类型
     */
    public Integer typeid;

}
