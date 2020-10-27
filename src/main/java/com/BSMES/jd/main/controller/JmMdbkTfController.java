package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.JmMdbkTfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jmmdbktf")
public class JmMdbkTfController {

    @Autowired
    JmMdbkTfService jmMdbkTfService;

    @GetMapping()
    public CommonReturn getVar(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmMdbkTfService.getMdbkTf(dto);
        }else{
            result = jmMdbkTfService.getMdbkTfPage(dto);
        }
        return result;
    }

}
