package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.InsmenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insmenu")
public class InsmenuController {

    @Autowired
    InsmenuService insmenuService;

    @GetMapping()
    public CommonReturn getVar(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = insmenuService.getInsmenu(dto);
        }else{
            result = insmenuService.getInsmenuPage(dto);
        }
        return result;
    }

}
