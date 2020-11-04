package com.BSMES.jd.main.dto;

import lombok.Data;

import java.util.List;

@Data
public class JmMdwx {

    JmMouldDTO jmMouldDTO;

    JmMdwxDTO jmMdwxDTO;

    List<JmMdwxTfDTO> jmMdwxTfDTOs;

}
