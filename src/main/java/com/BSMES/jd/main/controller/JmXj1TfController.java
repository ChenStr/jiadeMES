package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmXj1TfDTO;
import com.BSMES.jd.main.dto.JmXjTfDTO;
import com.BSMES.jd.main.service.JmXj1TfService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jmxj1tf")
public class JmXj1TfController {

    @Autowired
    JmXj1TfService jmXj1TfService;

    @GetMapping()
    public CommonReturn getXjTf(JmXj1TfDTO dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmXj1TfService.getXj1Tf(dto);
        }else{
            QueryWrapper queryWrapper = new QueryWrapper();
            result = jmXj1TfService.getXj1TfPage(dto,queryWrapper);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveXjTf(@RequestBody JmXj1TfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmXj1TfService.saveXj1Tf(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editXjTf(@RequestBody JmXj1TfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmXj1TfService.editXj1Tf(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delXjTf( String[] sids , Integer[] cids , String[] spcNos){
        CommonReturn result = new CommonReturn();
        List<String> sids1 = java.util.Arrays.asList(sids);
        List<Integer> cids1 = java.util.Arrays.asList(cids);
        List<String> spcNos1 = java.util.Arrays.asList(spcNos);
        result = jmXj1TfService.delXj1Tf(sids1,cids1,spcNos1);
        return result;
    }
}
