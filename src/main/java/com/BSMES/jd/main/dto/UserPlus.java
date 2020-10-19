package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import lombok.Data;

import java.util.List;

@Data
public class UserPlus extends BaseDTO {

    /**
     * 账户信息
     */
    InsuserDTO insuserDTO;

    /**
     * 人员信息
     */
    JmWorkerDTO jmWorkerDTO;

    /**
     * 与人员信息
     */
//    List<JmDevSalDTO> jmDevSalDTOS;

    /**
     * 设备信息
     */
    List<JmDevDTO> jmDevDTOS;


}
