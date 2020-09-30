package com.BSMES.jd.main.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class XjMtf {

    public JmXjMfDTO xjMf;

    public List<JmXjTfDTO> xjTf;

}
