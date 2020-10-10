package com.BSMES.jd.main.controller;
import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmXj2TfDTO;
import com.BSMES.jd.main.service.JmXj2TfService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/xj2tf")
@RestController
public class JmXj2TfController {

    @Autowired
    JmXj2TfService jmXj2TfService;

    @GetMapping()
    public CommonReturn getXj2Tf(JmXj2TfDTO dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmXj2TfService.getXj2Tf(dto);
        }else{
            QueryWrapper queryWrapper = new QueryWrapper();
            result = jmXj2TfService.getXj2TfPage(dto,queryWrapper);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveXj2Tf(@RequestBody List<JmXj2TfDTO> dtos){
        CommonReturn result = new CommonReturn();
        result = jmXj2TfService.saveXj2Tfs(dtos);
        return result;
    }

    @PutMapping()
    public CommonReturn editXj2Tf(@RequestBody JmXj2TfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmXj2TfService.editXj2Tf(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delXj2Tf( String[] sids , Integer[] cids ){
        CommonReturn result = new CommonReturn();
        List<String> sids1 = java.util.Arrays.asList(sids);
        List<Integer> cids1 = java.util.Arrays.asList(cids);
        result = jmXj2TfService.delXj2Tf(sids1,cids1);
        return result;
    }


}
