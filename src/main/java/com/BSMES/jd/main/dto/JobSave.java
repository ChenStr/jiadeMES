package com.BSMES.jd.main.dto;
import com.BSMES.jd.common.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class JobSave extends BaseDTO {

    /**
     * 调度单信息
     */
    JmMoMfDTO jmMoMfDTO;

    /**
     * 计划单数据
     */
    List<JmJobDTO> jmJobDTOS;
}
