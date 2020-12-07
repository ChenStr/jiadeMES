package com.BSMES.jd.main.dto;

import lombok.Data;

import java.util.List;

@Data
public class JmGzstd {

    public JmGzstdMfDTO jmGzstdMfDTO;

    public List<JmGzstdTfDTO> jmGzstdTfDTOS;

    public JmMouldDTO jmMouldDTO;

    public List<JmWxIdDTO> jmWxIdDTO;

}
