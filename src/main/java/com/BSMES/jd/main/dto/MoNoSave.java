package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import lombok.Data;

import java.util.List;

@Data
public class MoNoSave extends BaseDTO {

    JmMoMfDTO jmMoMfDTO;

    List<JobAndRec> jobs;

}
