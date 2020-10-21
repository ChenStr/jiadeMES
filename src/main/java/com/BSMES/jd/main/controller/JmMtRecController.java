package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmMtRec;
import com.BSMES.jd.main.dto.JmMtRecDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.JmMtRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/jmmtrec")
@RestController
public class JmMtRecController {

    @Autowired
    JmMtRecService jmMtRecService;

    @GetMapping()
    public CommonReturn getJmMtRec(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmMtRecService.getMtRec(dto);
        }else{
            result = jmMtRecService.getMtRecPage(dto);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveJmDevSal(@RequestBody JmMtRec dto){
        CommonReturn result = new CommonReturn();
        result = jmMtRecService.saveMtRec(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editJmDevSal(@RequestBody JmMtRecDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmMtRecService.editMtRec(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delJmDevSal(String[] wxNos ){
        CommonReturn result = new CommonReturn();
        List<String> wxNos1 = java.util.Arrays.asList(wxNos);
        result = jmMtRecService.delMtRec(wxNos1);
        return result;
    }

}
