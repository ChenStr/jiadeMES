package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmXj2TfDTO;
import com.BSMES.jd.main.dto.JmXj3TfDTO;
import com.BSMES.jd.main.service.JmXj3TfService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xj3tf")
public class JmXj3TfController {

    @Autowired
    JmXj3TfService jmXj3TfService;

    @GetMapping()
    public CommonReturn getMould(JmXj3TfDTO dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmXj3TfService.getXj3Tf(dto);
        }else{
            QueryWrapper queryWrapper = new QueryWrapper();
            result = jmXj3TfService.getXj3TfPage(dto,queryWrapper);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveMould(@RequestBody JmXj3TfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmXj3TfService.saveXj3Tf(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editMould(@RequestBody JmXj3TfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmXj3TfService.editXj3Tf(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delMould( String[] sids , Integer[] cids ,String[] chkNos ){
        CommonReturn result = new CommonReturn();
        List<String> sids1 = java.util.Arrays.asList(sids);
        List<Integer> cids1 = java.util.Arrays.asList(cids);
        List<String> chkNos1 = java.util.Arrays.asList(chkNos);
        result = jmXj3TfService.delXj3Tf(sids1,cids1,chkNos1);
        return result;
    }

}
