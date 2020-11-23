package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmChkSpcDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.JmChkSpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jmchkspc")
public class JmChkSpcController {

    @Autowired
    JmChkSpcService jmChkSpcService;

    @GetMapping()
    public CommonReturn getVar(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmChkSpcService.getJmChkSpc(dto);
        }else{
            result = jmChkSpcService.getJmChkSpcPage(dto);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveVar(@RequestBody JmChkSpcDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmChkSpcService.saveJmChkSpc(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editVar(@RequestBody JmChkSpcDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmChkSpcService.editJmChkSpc(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delVar( String[] spcChks ){
        CommonReturn result = new CommonReturn();
        List<String> spcChks1 = java.util.Arrays.asList(spcChks);
        result = jmChkSpcService.delJmChkSpc(spcChks1);
        return result;
    }

}
