package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import lombok.Data;

import java.util.List;

@Data
public class JobAndRec extends BaseDTO {

    /**
     * 计划单信息
     */
    JmJobDTO jobDTO;

    /**
     * 随工单信息
     */
    List<JobRecSave> jobRecSaves;

}
