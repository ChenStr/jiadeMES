package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.InsuserDTO;
import com.BSMES.jd.main.service.InsuserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户路由
 */
@RequestMapping("/user")
@RestController
public class InsuserController {

    @Autowired
    InsuserService insuserService;

    @GetMapping()
    public CommonReturn getInsuser(InsuserDTO dto,Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = insuserService.getUser(dto);
        }else{
            QueryWrapper queryWrapper = new QueryWrapper();
            result = insuserService.getUserPage(dto,queryWrapper);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveInsuser(@RequestBody InsuserDTO dto){
        CommonReturn result = new CommonReturn();
        result = insuserService.saveUser(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editInsuser(@RequestBody InsuserDTO dto){
        CommonReturn result = new CommonReturn();
        result = insuserService.editUser(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delInsuser( String[] ids ){
        CommonReturn result = new CommonReturn();
        List<String> usrcodes = java.util.Arrays.asList(ids);
        result = insuserService.delUser(usrcodes);
        return result;
    }

}
