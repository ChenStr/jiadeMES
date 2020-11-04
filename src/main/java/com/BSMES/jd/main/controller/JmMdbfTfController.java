package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.JmMdbfTfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/jmmdbftf")
@RestController
public class JmMdbfTfController {

    @Autowired
    JmMdbfTfService jmMdbfTfService;

    @GetMapping()
    public CommonReturn getVar(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmMdbfTfService.getMdbfTf(dto);
        }else{
            result = jmMdbfTfService.getMdbfTfPage(dto);
        }
        return result;
    }

}
