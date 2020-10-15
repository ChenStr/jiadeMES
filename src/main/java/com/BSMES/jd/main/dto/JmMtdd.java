package com.BSMES.jd.main.dto;

import lombok.Data;

import java.util.List;

@Data
public class JmMtdd {

    /**
     * 点检表表头
     */
    public JmMtddMfDTO jmMtddMfDTO;

    /**
     * 点检表表身
     */
    public List<JmMtdd2TfDTO> jmMtdd2TfDTOS;

    /**
     * 用户预设的检点项目
     */
    public List<JmDevMtidDTO> jmDevMtidDTO;

    

}
