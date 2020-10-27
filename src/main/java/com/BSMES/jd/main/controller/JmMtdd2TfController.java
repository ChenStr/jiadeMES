package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.JmMtdd2TfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mtdd2tf")
public class JmMtdd2TfController {

    @Autowired
    JmMtdd2TfService jmMtdd2TfService;

    @GetMapping
    public CommonReturn getVar(ResultType dto){
        CommonReturn result = new CommonReturn();
        result =  jmMtdd2TfService.getMtdd2(dto);
        return result;
    }

}
