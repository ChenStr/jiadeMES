package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmGzstd;
import com.BSMES.jd.main.dto.JmGzstdMfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.JmGzstdMfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/jmgzstdmf")
public class JmGzstdMfController {

    @Autowired
    JmGzstdMfService jmGzstdMfService;


    @GetMapping()
    public CommonReturn getVar(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmGzstdMfService.getGzstd(dto);
        }else{
            result = jmGzstdMfService.getGzstdPage(dto);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveVar(@RequestBody JmGzstd dto){
        CommonReturn result = new CommonReturn();
        result = jmGzstdMfService.saveGzstd(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editVar(@RequestBody JmGzstdMfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmGzstdMfService.editGzstd(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delVar( String[] gzstdNos ){
        CommonReturn result = new CommonReturn();
        List<String> gzstdNos1 = java.util.Arrays.asList(gzstdNos);
        result = jmGzstdMfService.delGzstd(gzstdNos1);
        return result;
    }

}
