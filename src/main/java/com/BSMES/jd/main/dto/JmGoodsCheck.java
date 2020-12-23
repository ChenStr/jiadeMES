package com.BSMES.jd.main.dto;

import lombok.Data;

import java.util.List;

@Data
public class JmGoodsCheck {

    public JmGoodsCheckMfDTO jmGoodsCheckMfDTO;

    public List<JmGoodsCheckTfDTO> jmGoodsCheckTfDTOS;

}
