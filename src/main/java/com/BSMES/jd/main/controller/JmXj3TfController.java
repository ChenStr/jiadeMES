package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmXj2TfDTO;
import com.BSMES.jd.main.dto.JmXj3TfDTO;
import com.BSMES.jd.main.dto.ResultType;
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
    public CommonReturn getJmXj3Tf(ResultType dto, Boolean isPage){
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
    public CommonReturn saveJmXj3Tf(@RequestBody List<JmXj3TfDTO> dtos){
        CommonReturn result = new CommonReturn();
        result = jmXj3TfService.saveXj3Tfs(dtos);
        return result;
    }

    @PutMapping()
    public CommonReturn editJmXj3Tf(@RequestBody JmXj3TfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmXj3TfService.editXj3Tf(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delJmXj3Tf( String[] sids , Integer[] cids ,String[] chkNos ){
        CommonReturn result = new CommonReturn();
        List<String> sids1 = java.util.Arrays.asList(sids);
        List<Integer> cids1 = java.util.Arrays.asList(cids);
        List<String> chkNos1 = java.util.Arrays.asList(chkNos);
        result = jmXj3TfService.delXj3Tf(sids1,cids1,chkNos1);
        return result;
    }

}
