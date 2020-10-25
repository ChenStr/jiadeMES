package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.JmMdly;
import com.BSMES.jd.main.dto.JmMdlyMfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.JmMdlyMfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 模具领用
 */
@RestController
@RequestMapping("/jmmdlymf")
public class JmMdlyMfController {

    @Autowired
    JmMdlyMfService jmMdlyMfService;

    @GetMapping()
    public CommonReturn getJmMdlyMf(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmMdlyMfService.getMdlyMf(dto);
        }else{
            result = jmMdlyMfService.getMdlyMfPage(dto);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveJmMdlyMf(@RequestBody JmMdly dto){
        CommonReturn result = new CommonReturn();
        result = jmMdlyMfService.saveMdlyMf(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editJmMdlyMf(@RequestBody JmMdlyMfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmMdlyMfService.editMdlyMf(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delJmMdlyMf( String[] ids ){
        CommonReturn result = new CommonReturn();
        List<String> snames = java.util.Arrays.asList(ids);
        result = jmMdlyMfService.delMdlyMf(snames);
        return result;
    }

}
