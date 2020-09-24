package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.InssysvarDTO;
import com.BSMES.jd.main.dto.InsuserDTO;
import com.BSMES.jd.main.service.InssysvarService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 编码路由
 */
@RequestMapping("/var")
@RestController
public class InssysvarController {

    @Autowired
    InssysvarService inssysvarService;

    @GetMapping()
    public CommonReturn getVar(InssysvarDTO dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = inssysvarService.getVar(dto);
        }else{
            QueryWrapper queryWrapper = new QueryWrapper();
            result = inssysvarService.getVarPage(dto,queryWrapper);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveVar(@RequestBody InssysvarDTO dto){
        CommonReturn result = new CommonReturn();
        result = inssysvarService.saveVar(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editVar(@RequestBody InssysvarDTO dto){
        CommonReturn result = new CommonReturn();
        result = inssysvarService.editVar(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delVar( String[] ids ){
        CommonReturn result = new CommonReturn();
        List<String> snames = java.util.Arrays.asList(ids);
        result = inssysvarService.delVar(snames);
        return result;
    }

}
