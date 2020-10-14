package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import lombok.Data;

@Data
public class UserAndWorker extends BaseDTO {

    /**
     * 账户信息
     */
    InsuserDTO insuserDTO;

    /**
     * 人员信息
     */
    JmWorkerDTO jmWorkerDTO;



}
