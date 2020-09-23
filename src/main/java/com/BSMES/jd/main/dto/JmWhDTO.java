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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JmWhDTO extends BaseDTO implements Serializable {

    /**
     * 仓库代号
     */
    @TableId
    private String wh;

    /**
     * 名称
     */
    private String name;

    /**
     * 属性
     */
    private String attrib;

    /**
     * 保管员
     */
    private String wkNo;

    /**
     * ERP仓库代号
     */
    private Integer whid;

}
