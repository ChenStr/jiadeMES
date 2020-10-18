package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.JmChkstdMfDTO;
import com.BSMES.jd.main.service.JmChkstdMfService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jmchkstdmf")
public class JmChkstdMfController {

    @Autowired
    JmChkstdMfService jmChkstdMfService;

    @GetMapping()
    public CommonReturn getJmChkstdMf(JmChkstdMfDTO dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmChkstdMfService.getChkstdMf(dto);
        }else{
            QueryWrapper queryWrapper = new QueryWrapper();
            result = jmChkstdMfService.getChkstdMfPage(dto,queryWrapper);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveJmChkstdMf(@RequestBody JmChkstdMfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmChkstdMfService.saveChkstdMf(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editVar(@RequestBody JmChkstdMfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmChkstdMfService.editChkstdMf(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delVar( String[] chkstdNos ){
        CommonReturn result = new CommonReturn();
        List<String> snames = java.util.Arrays.asList(chkstdNos);
        result = jmChkstdMfService.delChkstdMf(snames);
        return result;
    }

}
