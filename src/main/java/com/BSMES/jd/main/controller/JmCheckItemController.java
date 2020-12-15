package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmCheckItemDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.JmCheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkItem")
public class JmCheckItemController {

    @Autowired
    JmCheckItemService jmCheckItemService;

    @GetMapping()
    public CommonReturn getVar(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmCheckItemService.getCheck(dto);
        }else{
            result = jmCheckItemService.getCheckPage(dto);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveVar(@RequestBody JmCheckItemDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmCheckItemService.saveCheck(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editVar(@RequestBody JmCheckItemDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmCheckItemService.editCheck(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delVar( String[] sids ){
        CommonReturn result = new CommonReturn();
        List<String> sids1 = java.util.Arrays.asList(sids);
        result = jmCheckItemService.delCheck(sids1);
        return result;
    }

}
