package com.BSMES.jd.main.dto;

import lombok.Data;

import java.util.List;

/**
 * 模具报废
 */
@Data
public class JmMdbf {

    JmMdbfMfDTO jmMdbfMfDTO;

    List<JmMdbfTfDTO> jmMdbfTfDTOS;

}
