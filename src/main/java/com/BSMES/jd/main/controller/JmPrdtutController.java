package com.BSMES.jd.main.controller;


import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmPrdtDTO;
import com.BSMES.jd.main.dto.JmPrdtutDTO;
import com.BSMES.jd.main.service.JmPrdtutService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/prdtut")
@RestController
public class JmPrdtutController {

    @Autowired
    JmPrdtutService jmPrdtutService;

    @GetMapping()
    public CommonReturn getPrdtut(JmPrdtutDTO dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmPrdtutService.getPrdtut(dto);
        }else{
            QueryWrapper queryWrapper = new QueryWrapper();
            result = jmPrdtutService.getPrdtutPage(dto,queryWrapper);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn savePrdtut(@RequestBody JmPrdtutDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmPrdtutService.savePrdtut(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editPrdtut(@RequestBody JmPrdtutDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmPrdtutService.editPrdtut(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delPrdtut( String[] ids ){
        CommonReturn result = new CommonReturn();
        List<String> ubms = java.util.Arrays.asList(ids);
        result = jmPrdtutService.delPrdtut(ubms);
        return result;
    }

}
