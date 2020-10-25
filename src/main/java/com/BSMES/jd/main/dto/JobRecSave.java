package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;


@Data
public class JobRecSave extends BaseDTO {

    JmJobRecDTO jmJobRecDTO;

    /**
     * 最终数据
     */
    List<JmJobRecBDTO> jmJobRecBDTOS;
}
