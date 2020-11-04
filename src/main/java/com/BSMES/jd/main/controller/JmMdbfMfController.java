package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.service.JmMdbfMfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/jmmdbfmf")
@RestController
public class JmMdbfMfController {

    @Autowired
    JmMdbfMfService jmMdbfMfService;

    @GetMapping()
    public CommonReturn getVar(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmMdbfMfService.getMdbfMf(dto);
        }else{
            result = jmMdbfMfService.getMdbfMfPage(dto);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveVar(@RequestBody JmMdbf dto){
        CommonReturn result = new CommonReturn();
        result = jmMdbfMfService.saveMdbf(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editVar(@RequestBody JmMdbfMfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmMdbfMfService.editMdbfMf(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delVar( String[] ids ){
        CommonReturn result = new CommonReturn();
        List<String> snames = java.util.Arrays.asList(ids);
        result = jmMdbfMfService.delMdbfMf(snames);
        return result;
    }

}
