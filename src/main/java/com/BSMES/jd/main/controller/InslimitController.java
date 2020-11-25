package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.InslimitDTO;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.InslimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inslimit")
public class InslimitController {

    @Autowired
    InslimitService inslimitService;

    @PostMapping("/role")
    public CommonReturn getInslimit(@RequestBody ResultType dto){
        CommonReturn result = new CommonReturn();
        result = inslimitService.getInsLimit(dto);
        return result;
    }

    @GetMapping()
    public CommonReturn getVar(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = inslimitService.getInslimit(dto);
        }else{
            result = inslimitService.getInslimitPage(dto);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveVar(@RequestBody InslimitDTO dto){
        CommonReturn result = new CommonReturn();
        result = inslimitService.saveInslimit(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editVar(@RequestBody InslimitDTO dto){
        CommonReturn result = new CommonReturn();
        result = inslimitService.editInslimit(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delVar( String[] gwusers,String[] menuids ){
        CommonReturn result = new CommonReturn();
        List<String> gwusers1 = java.util.Arrays.asList(gwusers);
        List<String> menuids1 = java.util.Arrays.asList(menuids);
        result = inslimitService.delInslimit(gwusers1,menuids1);
        return result;
    }

}
