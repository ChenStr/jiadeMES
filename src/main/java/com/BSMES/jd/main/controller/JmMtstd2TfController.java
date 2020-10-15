package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmMtdd2TfDTO;
import com.BSMES.jd.main.service.JmMtdd2TfService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/mtstd2tf")
@RestController
public class JmMtstd2TfController {

    @Autowired
    JmMtdd2TfService jmMtdd2TfService;

    @GetMapping()
    public CommonReturn getMtId(JmMtdd2TfDTO dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmMtdd2TfService.getMtdd2(dto);
        }else{
            QueryWrapper queryWrapper = new QueryWrapper();
            result = jmMtdd2TfService.getMtdd2Page(dto,queryWrapper);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveMtId(@RequestBody JmMtdd2TfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmMtdd2TfService.saveMtdd2(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editMtId(@RequestBody JmMtdd2TfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmMtdd2TfService.editMtdd2(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delMould( String[] sids , Integer[] cids ){
        CommonReturn result = new CommonReturn();
        List<String> sids1 = java.util.Arrays.asList(sids);
        List<Integer> cids1 = java.util.Arrays.asList(cids);
        result = jmMtdd2TfService.delMtdd2(sids1,cids1);
        return result;
    }

}
