package com.BSMES.jd.main.controller;


import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.InsuserDTO;
import com.BSMES.jd.main.dto.JmMouldDTO;
import com.BSMES.jd.main.service.JmMouldService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/mould")
@RestController
public class JmMouldController {

    @Autowired
    JmMouldService jmMouldService;

    @GetMapping()
    public CommonReturn getMould(JmMouldDTO dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmMouldService.getMould(dto);
        }else{
            QueryWrapper queryWrapper = new QueryWrapper();
            result = jmMouldService.getMoMfPage(dto,queryWrapper);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveMould(@RequestBody JmMouldDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmMouldService.saveMould(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editMould(@RequestBody JmMouldDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmMouldService.editMould(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delMould( String[] ids , Integer[] typeids ){
        CommonReturn result = new CommonReturn();
        List<String> moNos = java.util.Arrays.asList(ids);
        List<Integer> types = java.util.Arrays.asList(typeids);
        result = jmMouldService.delMould(moNos,types);
        return result;
    }

}
