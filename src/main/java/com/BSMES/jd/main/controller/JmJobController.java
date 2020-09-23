package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmDevDTO;
import com.BSMES.jd.main.dto.JmJobDTO;
import com.BSMES.jd.main.service.JmJobService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
public class JmJobController {

    @Autowired
    JmJobService jmJobService;

    @GetMapping()
    public CommonReturn getJmJob(JmJobDTO dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmJobService.getJob(dto);
        }else{
            QueryWrapper queryWrapper = new QueryWrapper();
            result = jmJobService.getJobPage(dto,queryWrapper);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveJmJob(@RequestBody JmJobDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmJobService.saveJob(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editJmJob(@RequestBody JmJobDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmJobService.editJob(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delJmJob(String[] ids, Integer[] cids){
        CommonReturn result = new CommonReturn();
        List<String> sids = java.util.Arrays.asList(ids);
        List<Integer> cids2 = java.util.Arrays.asList(cids);
        result = jmJobService.delJob(sids,cids2);
        return result;
    }

}
