package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * 单位映射表
 */
@Data
public class JmPrdtutDTO extends BaseDTO implements Serializable {

    /**
     * 单位
     */
    public String ubm;

    /**
     * 单位名称
     */
    public String umc;

}
