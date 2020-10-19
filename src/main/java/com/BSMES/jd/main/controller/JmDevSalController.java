package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmChkstdTfDTO;
import com.BSMES.jd.main.dto.JmDevSalDTO;
import com.BSMES.jd.main.service.JmDevSalService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jmdevsal")
public class JmDevSalController {

    @Autowired
    JmDevSalService jmDevSalService;


    @GetMapping()
    public CommonReturn getJmDevSal(JmDevSalDTO dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmDevSalService.getDevSal(dto);
        }else{
            QueryWrapper queryWrapper = new QueryWrapper();
            result = jmDevSalService.getDevSalPage(dto,queryWrapper);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveJmChkstdTf(@RequestBody JmDevSalDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmDevSalService.saveDevSal(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editVar(@RequestBody JmDevSalDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmDevSalService.editDevSal(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delVar(Integer[] cids ){
        CommonReturn result = new CommonReturn();
        List<Integer> cids1 = java.util.Arrays.asList(cids);
        result = jmDevSalService.delDevSal(cids1);
        return result;
    }

}
