package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * 仓库映射表
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JmPrdtutDTO extends BaseDTO implements Serializable {

    /**
     * 单位
     */
    private String ubm;

    /**
     * 单位名称
     */
    private String umc;

}
