package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmMtIdDTO;
import com.BSMES.jd.main.dto.JmWhDTO;
import com.BSMES.jd.main.service.JmMtIdService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mtid")
public class JmMtIdController {

    @Autowired
    JmMtIdService jmMtIdService;

    @GetMapping()
    public CommonReturn getMtId(JmMtIdDTO dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmMtIdService.getMtId(dto);
        }else{
            QueryWrapper queryWrapper = new QueryWrapper();
            result = jmMtIdService.getMtIdPage(dto,queryWrapper);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveMtId(@RequestBody JmMtIdDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmMtIdService.saveMtId(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editMtId(@RequestBody JmMtIdDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmMtIdService.editMtId(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delMtId( String[] mtIds ){
        CommonReturn result = new CommonReturn();
        List<String> mtIds1 = java.util.Arrays.asList(mtIds);
        result = jmMtIdService.delMtId(mtIds1);
        return result;
    }

}
