package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmMoMfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.JmMoMfService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 调度单路由(制令单)
 */
@RequestMapping("/momf")
@RestController
public class JmMoMfController {

    @Autowired
    JmMoMfService jmMoMfService;

    @GetMapping()
    public CommonReturn getJmMoMf(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmMoMfService.getMoMf(dto);
        }else{
            result = jmMoMfService.getMoMfPage(dto);
        }
        return result;
    }

    @GetMapping("/detailed")
    public CommonReturn getMoNo(ResultType dto){
        CommonReturn result = new CommonReturn();
        result = jmMoMfService.getMoNo(dto);
        return result;
    }

    @PostMapping()
    public CommonReturn saveJmMoMf(@RequestBody JmMoMfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmMoMfService.saveMoMf(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editJmMoMf(@RequestBody JmMoMfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmMoMfService.editMoMf(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delJmMoMf( String[] ids ){
        CommonReturn result = new CommonReturn();
        List<String> wkNos = java.util.Arrays.asList(ids);
        result = jmMoMfService.delMoMf(wkNos);
        return result;
    }

}
