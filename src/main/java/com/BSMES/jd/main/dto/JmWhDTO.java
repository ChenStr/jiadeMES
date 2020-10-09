package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * 仓库表映射
 */
@Data
public class JmWhDTO extends BaseDTO implements Serializable {

    /**
     * 仓库代号
     */
    @TableId
    public String wh;

    /**
     * 名称
     */
    public String name;

    /**
     * 属性
     */
    public String attrib;

    /**
     * 保管员
     */
    public String wkNo;

    /**
     * ERP仓库代号
     */
    public Integer whid;

}
