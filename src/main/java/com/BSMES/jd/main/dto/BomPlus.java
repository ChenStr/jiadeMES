package com.BSMES.jd.main.dto;

import lombok.Data;

import java.util.List;

/**
 * Bom表头与表身
 */
@Data
public class BomPlus {

    /**
     * bom表表头
     */
    public JmBomMfDTO jmBomMfDTO;

    /**
     * bom表表身
     */
    public List<JmBomTfDTO> jmBomTfDTOS;

}
