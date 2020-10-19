package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmChkstdMfDTO;
import com.BSMES.jd.main.dto.JmChkstdTfDTO;
import com.BSMES.jd.main.service.JmChkstdMfService;
import com.BSMES.jd.main.service.JmChkstdTfService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jmchkstdtf")
public class JmChkstdTfController {

    @Autowired
    JmChkstdTfService jmChkstdTfService;

    @GetMapping()
    public CommonReturn getJmChkstdTf(JmChkstdTfDTO dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmChkstdTfService.getChkstdTf(dto);
        }else{
            QueryWrapper queryWrapper = new QueryWrapper();
            result = jmChkstdTfService.getChkstdTfPage(dto,queryWrapper);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveJmChkstdTf(@RequestBody JmChkstdTfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmChkstdTfService.saveChkstdTf(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editVar(@RequestBody JmChkstdTfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmChkstdTfService.editChkstdTf(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delVar( String[] chkstdNos,Integer[] cids ){
        CommonReturn result = new CommonReturn();
        List<String> chkstdNos1 = java.util.Arrays.asList(chkstdNos);
        List<Integer> cids1 = java.util.Arrays.asList(cids);
        result = jmChkstdTfService.delChkstdTf(chkstdNos1,cids1);
        return result;
    }

}
