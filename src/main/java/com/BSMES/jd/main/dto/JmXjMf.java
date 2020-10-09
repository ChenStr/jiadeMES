package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class JmXjMf extends BaseDTO implements Serializable {

    JmXj2TfDTO jmXj2TfDTO;

    List<JmXj3TfDTO> jmXj3TfDTOS;

}
