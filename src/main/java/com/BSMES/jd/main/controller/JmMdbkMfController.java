package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmMdbk;
import com.BSMES.jd.main.dto.JmMdbkMfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.JmMdbkMfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jmmdbkmf")
public class JmMdbkMfController {

    @Autowired
    JmMdbkMfService jmMdbkMfService;

    @GetMapping()
    public CommonReturn getVar(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmMdbkMfService.getMdbkMf(dto);
        }else{
            result = jmMdbkMfService.getMdbkMfPage(dto);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveVar(@RequestBody JmMdbk dto){
        CommonReturn result = new CommonReturn();
        result = jmMdbkMfService.saveMdbk(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editVar(@RequestBody JmMdbkMfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmMdbkMfService.editMdbkMf(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delVar( String[] ids ){
        CommonReturn result = new CommonReturn();
        List<String> snames = java.util.Arrays.asList(ids);
        result = jmMdbkMfService.delMdbkMf(snames);
        return result;
    }

    @GetMapping("/day")
    public CommonReturn getMdNoDay(ResultType dto){
        CommonReturn result = new CommonReturn();
        result = jmMdbkMfService.getDay(dto);
        return result;

    }

}
