package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmWorkerDTO;
import com.BSMES.jd.main.service.JmWorkerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 人员路由
 */
@RequestMapping("/worker")
@RestController
public class JmWorkerController {

    @Autowired
    JmWorkerService jmWorkerService;

    @GetMapping()
    public CommonReturn getJmWorker(JmWorkerDTO dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmWorkerService.getWorker(dto);
        }else{
            QueryWrapper queryWrapper = new QueryWrapper();
            result = jmWorkerService.getWorkerPage(dto,queryWrapper);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveJmWorker(@RequestBody JmWorkerDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmWorkerService.saveWorker(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editJmWorker(@RequestBody JmWorkerDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmWorkerService.editWorker(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delJmWorker( String[] ids ){
        CommonReturn result = new CommonReturn();
        List<String> wkNos = java.util.Arrays.asList(ids);
        result = jmWorkerService.delWorker(wkNos);
        return result;
    }

}
