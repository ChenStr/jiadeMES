package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.JmWhLocDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.JmWhLocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jmwhloc")
public class JmWhLocController {

    @Autowired
    JmWhLocService jmWhLocService;

    @GetMapping()
    public CommonReturn getVar(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmWhLocService.getWhLoc(dto);
        }else{
            result = jmWhLocService.getWhLocPage(dto);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveVar(@RequestBody JmWhLocDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmWhLocService.saveWhLoc(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editVar(@RequestBody JmWhLocDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmWhLocService.editWhLoc(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delVar( String[] whLocs ){
        CommonReturn result = new CommonReturn();
        List<String> whLocs1 = java.util.Arrays.asList(whLocs);
        result = jmWhLocService.delWhLoc(whLocs1);
        return result;
    }

}
