package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmDevDTO;
import com.BSMES.jd.main.service.JmDevService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设备路由
 */
@RequestMapping("/dev")
@RestController
public class JmDevController {

    @Autowired
    JmDevService jmDevService;

    @GetMapping()
    public CommonReturn getJmDev(JmDevDTO dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmDevService.getDev(dto);
        }else{
            QueryWrapper queryWrapper = new QueryWrapper();
            result = jmDevService.getDevPage(dto,queryWrapper);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveJmDev(@RequestBody JmDevDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmDevService.saveDev(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editJmDev(@RequestBody JmDevDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmDevService.editDev(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delJmDev( String[] ids ){
        CommonReturn result = new CommonReturn();
        List<String> devNos = java.util.Arrays.asList(ids);
        result = jmDevService.delDev(devNos);
        return result;
    }

}
