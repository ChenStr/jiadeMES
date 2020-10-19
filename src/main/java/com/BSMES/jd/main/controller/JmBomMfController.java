package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.JmBomMfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.JmBomMfService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jmbommf")
public class JmBomMfController{

    @Autowired
    JmBomMfService jmBomMfService;

    @GetMapping()
    public CommonReturn getJmBomMf(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmBomMfService.getBomPlus(dto);
        }else{
            result = jmBomMfService.getBomPlusPage(dto);
        }
        return result;
    }



}
