package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备保养检点表身(维修保养项目)
 */
@Data
public class JmMtstdTf extends BaseDTO implements Serializable {

    public JmMtstdTfDTO jmMtstdTf;

    public JmMtIdDTO jmMtIdDTO;

}
