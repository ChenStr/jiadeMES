package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.JmOutMouldDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.InsorgService;
import com.BSMES.jd.main.service.JmOutMouldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jmoutmould")
public class JmOutMouldController {

    @Autowired
    JmOutMouldService jmOutMouldService;

    @GetMapping()
    public CommonReturn getVar(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmOutMouldService.getOut(dto);
        }else{
            result = jmOutMouldService.getOutPage(dto);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveVar(@RequestBody JmOutMouldDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmOutMouldService.saveOut(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editVar(@RequestBody JmOutMouldDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmOutMouldService.editOut(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delVar( String[] sids ){
        CommonReturn result = new CommonReturn();
        List<String> sids1 = java.util.Arrays.asList(sids);
        result = jmOutMouldService.delOut(sids1);
        return result;
    }

}
