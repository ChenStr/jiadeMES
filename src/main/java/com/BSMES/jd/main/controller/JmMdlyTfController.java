package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.JmMdbkTfService;
import com.BSMES.jd.main.service.JmMdlyTfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jmmdlytf")
public class JmMdlyTfController {

    @Autowired
    JmMdlyTfService jmMdlyTfService;

    @GetMapping()
    public CommonReturn getVar(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmMdlyTfService.getMdlyTf(dto);
        }else{
            result = jmMdlyTfService.getMdlyTfPage(dto);
        }
        return result;
    }

    @GetMapping("/plus")
    public CommonReturn getMdly(ResultType dto){
        CommonReturn result = new CommonReturn();
        result = jmMdlyTfService.getMdly(dto);
        return result;
    }

}
