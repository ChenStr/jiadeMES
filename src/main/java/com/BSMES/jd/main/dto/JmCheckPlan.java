package com.BSMES.jd.main.dto;

import lombok.Data;

import java.util.List;

@Data
public class JmCheckPlan {

    public JmCheckPlanMfDTO jmCheckPlanMfDTO;

    public List<JmCheckPlanTfDTO> jmCheckPlanTfDTOS;

}
