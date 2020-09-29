package com.BSMES.jd.main.controller;


import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmJobRecBDTO;
import com.BSMES.jd.main.service.JmJobRecBService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/jobrecb")
@RestController
public class JmJobRecBController {

    @Autowired
    JmJobRecBService jmJobRecBService;

    @GetMapping()
    public CommonReturn getJobRecB(JmJobRecBDTO dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmJobRecBService.getJobRecB(dto);
        }else{
            QueryWrapper queryWrapper = new QueryWrapper();
            result = jmJobRecBService.getJobRecBPage(dto,queryWrapper);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveJobRecB(@RequestBody JmJobRecBDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmJobRecBService.saveJobRecB(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editJobRecB(@RequestBody JmJobRecBDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmJobRecBService.editJobRecB(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delJobRecB( String[] opsids , Integer[] cids ){
        CommonReturn result = new CommonReturn();
        List<String> opsids1 = java.util.Arrays.asList(opsids);
        List<Integer> cids1 = java.util.Arrays.asList(cids);
        result = jmJobRecBService.delJobRecB(opsids1,cids1);
        return result;
    }


}
