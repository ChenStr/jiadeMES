package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.JmWxIdDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.JmWxIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/jmwxid")
@RestController
public class JmWxIdController {

    @Autowired
    JmWxIdService jmWxIdService;

    @GetMapping()
    public CommonReturn getVar(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmWxIdService.getJmWxId(dto);
        }else{
            result = jmWxIdService.getJmWxIdPage(dto);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveVar(@RequestBody JmWxIdDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmWxIdService.saveJmWxId(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editVar(@RequestBody JmWxIdDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmWxIdService.editJmWxId(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delVar( String[] mtIds ){
        CommonReturn result = new CommonReturn();
        List<String> mtIds1 = java.util.Arrays.asList(mtIds);
        result = jmWxIdService.delJmWxId(mtIds1);
        return result;
    }

}
