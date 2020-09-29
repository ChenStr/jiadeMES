package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmWorkerDTO;
import com.BSMES.jd.main.dto.JmXjMfDTO;
import com.BSMES.jd.main.service.JmXjMfService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xjmf")
public class JmXjMfController {

    @Autowired
    JmXjMfService jmXjMfService;

    @GetMapping()
    public CommonReturn getJmXjMf(JmXjMfDTO dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmXjMfService.getXjMf(dto);
        }else{
            QueryWrapper queryWrapper = new QueryWrapper();
            result = jmXjMfService.getXjMfPage(dto,queryWrapper);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveJmXjMf(@RequestBody JmXjMfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmXjMfService.saveXjMf(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editJmXjMf(@RequestBody JmXjMfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmXjMfService.editXjMf(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delJmXjMf( String[] sids ){
        CommonReturn result = new CommonReturn();
        List<String> sids1 = java.util.Arrays.asList(sids);
        result = jmXjMfService.delXjMf(sids1);
        return result;
    }

}
