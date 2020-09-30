package com.BSMES.jd.main.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JobRecSave {

    JmJobRecDTO jmJobRecDTO;

    List<JmJobRecBDTO> jmJobRecBDTOS;

}
