package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.io.Serializable;

/**
 * 检点项目(维修保养项目)
 */
@Data
public class JmMtIdDTO extends BaseDTO implements Serializable {

    /**
     * 项目代号
     */
    public String mtId;

    /**
     * 名称
     */
    public String name;

}
