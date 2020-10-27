package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * 模具还回类
 */
@Data
public class JmMdbk extends BaseDTO {

    public JmMdbkMfDTO jmMdbkMfDTO;

    public List<JmMdbkTfDTO> jmMdbkTfDTOS;

}
