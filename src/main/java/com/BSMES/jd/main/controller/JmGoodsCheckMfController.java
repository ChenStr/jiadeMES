package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.service.JmCheckPlanMfService;
import com.BSMES.jd.main.service.JmGoodsCheckMfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goodCheckMf")
public class JmGoodsCheckMfController {

    @Autowired
    JmGoodsCheckMfService jmGoodsCheckMfService;

    @GetMapping()
    public CommonReturn getVar(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmGoodsCheckMfService.getGoods(dto);
        }else{
            result = jmGoodsCheckMfService.getGoodsPage(dto);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveVar(@RequestBody JmGoodsCheck dto){
        CommonReturn result = new CommonReturn();
        result = jmGoodsCheckMfService.saveGoods(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editVar(@RequestBody JmGoodsCheckMfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmGoodsCheckMfService.editGoods(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delVar( String[] sids){
        CommonReturn result = new CommonReturn();
        List<String> sids1 = java.util.Arrays.asList(sids);
        result = jmGoodsCheckMfService.delGoods(sids1);
        return result;
    }

}
