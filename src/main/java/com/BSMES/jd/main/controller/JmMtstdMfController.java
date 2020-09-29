package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmMtIdDTO;
import com.BSMES.jd.main.dto.JmMtstdMfDTO;
import com.BSMES.jd.main.service.JmMtstdMfService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/jmmtstdmf")
@RestController
public class JmMtstdMfController {

    @Autowired
    JmMtstdMfService jmMtstdMfService;

    @GetMapping()
    public CommonReturn getMtId(JmMtstdMfDTO dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmMtstdMfService.getMtstdMf(dto);
        }else{
            QueryWrapper queryWrapper = new QueryWrapper();
            result = jmMtstdMfService.getMtstdMfPage(dto,queryWrapper);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveMtId(@RequestBody JmMtstdMfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmMtstdMfService.saveMtstdMf(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editMtId(@RequestBody JmMtstdMfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmMtstdMfService.editMtstdMf(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delMtId( String[] mtstdNos ){
        CommonReturn result = new CommonReturn();
        List<String> mtstdNos1 = java.util.Arrays.asList(mtstdNos);
        result = jmMtstdMfService.delMtstdMf(mtstdNos1);
        return result;
    }

}
