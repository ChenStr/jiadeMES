package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmMtstdTfDTO;
import com.BSMES.jd.main.service.JmMtstdTfService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jmmtstdtf")
public class JmMtstdTfController {

    @Autowired
    JmMtstdTfService jmMtstdTfService;

    @GetMapping()
    public CommonReturn getMtstdTf(JmMtstdTfDTO dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmMtstdTfService.getMtstdTf(dto);
        }else{
            QueryWrapper queryWrapper = new QueryWrapper();
            result = jmMtstdTfService.getMtstdTfPage(dto,queryWrapper);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveMtstdTf(@RequestBody JmMtstdTfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmMtstdTfService.saveMtstdTf(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editMtstdTf(@RequestBody JmMtstdTfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmMtstdTfService.editMtstdTf(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delMtstdTf( String[] mtstdNos , Integer[] cids ){
        CommonReturn result = new CommonReturn();
        List<String> mtstdNos1 = java.util.Arrays.asList(mtstdNos);
        List<Integer> cids1 = java.util.Arrays.asList(cids);
        result = jmMtstdTfService.delMtstdTf(mtstdNos1,cids1);
        return result;
    }

}
