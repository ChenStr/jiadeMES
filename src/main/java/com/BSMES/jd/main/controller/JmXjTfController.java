package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmMouldDTO;
import com.BSMES.jd.main.dto.JmXjTfDTO;
import com.BSMES.jd.main.service.JmXjTfService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xjtf")
public class JmXjTfController {

    @Autowired
    JmXjTfService jmXjTfService;

    @GetMapping()
    public CommonReturn getXjTf(JmXjTfDTO dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmXjTfService.getXjTf(dto);
        }else{
            QueryWrapper queryWrapper = new QueryWrapper();
            result = jmXjTfService.getXjTfPage(dto,queryWrapper);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveXjTf(@RequestBody JmXjTfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmXjTfService.saveXjTf(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editXjTf(@RequestBody JmXjTfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmXjTfService.editXjTf(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delXjTf( String[] sids , Integer[] cids ){
        CommonReturn result = new CommonReturn();
        List<String> sids1 = java.util.Arrays.asList(sids);
        List<Integer> cids1 = java.util.Arrays.asList(cids);
        result = jmXjTfService.delXjTf(sids1,cids1);
        return result;
    }

}
