package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.InssysvarDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.InsorgService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sorg")
public class InsorgController {

    @Autowired
    InsorgService insorgService;

    @GetMapping()
    public CommonReturn getVar(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = insorgService.getSorg(dto);
        }else{
            result = insorgService.getSorgPage(dto);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveVar(@RequestBody InsorgDTO dto){
        CommonReturn result = new CommonReturn();
        result = insorgService.saveSorg(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editVar(@RequestBody InsorgDTO dto){
        CommonReturn result = new CommonReturn();
        result = insorgService.editSorg(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delVar( String[] ids ){
        CommonReturn result = new CommonReturn();
        List<String> snames = java.util.Arrays.asList(ids);
        result = insorgService.delSorg(snames);
        return result;
    }

}
