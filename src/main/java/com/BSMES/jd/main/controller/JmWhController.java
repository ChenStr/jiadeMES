package com.BSMES.jd.main.controller;


import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmWhDTO;
import com.BSMES.jd.main.dto.JmWorkerDTO;
import com.BSMES.jd.main.service.JmWhService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wh")
public class JmWhController {

    @Autowired
    JmWhService jmWhService;

    @GetMapping()
    public CommonReturn getJmWh(JmWhDTO dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmWhService.getWh(dto);
        }else{
            QueryWrapper queryWrapper = new QueryWrapper();
            result = jmWhService.getWhPage(dto,queryWrapper);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveJmWh(@RequestBody JmWhDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmWhService.saveWh(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editJmWh(@RequestBody JmWhDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmWhService.editWh(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delJmWh( String[] ids ){
        CommonReturn result = new CommonReturn();
        List<String> wkNos = java.util.Arrays.asList(ids);
        result = jmWhService.delWh(wkNos);
        return result;
    }

}
