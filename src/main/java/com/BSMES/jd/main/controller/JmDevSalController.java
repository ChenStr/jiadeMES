package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmChkstdTfDTO;
import com.BSMES.jd.main.dto.JmDevSalDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.JmDevSalService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jmdevsal")
public class JmDevSalController {

    @Autowired
    JmDevSalService jmDevSalService;


    @GetMapping()
    public CommonReturn getJmDevSal(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmDevSalService.getDevSal(dto);
        }else{
            result = jmDevSalService.getDevSalPage(dto);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveJmDevSal(@RequestBody List<JmDevSalDTO> dtos){
        CommonReturn result = new CommonReturn();
        result = jmDevSalService.saveDevSals(dtos);
        return result;
    }

    @PutMapping()
    public CommonReturn editJmDevSal(@RequestBody JmDevSalDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmDevSalService.editDevSal(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delJmDevSal(String[] devNos,String[] salNos ){
        CommonReturn result = new CommonReturn();
        List<String> devNos1 = java.util.Arrays.asList(devNos);
        List<String> salNos1 = java.util.Arrays.asList(salNos);
        result = jmDevSalService.delDevSal(devNos1,salNos1);
        return result;
    }

}
