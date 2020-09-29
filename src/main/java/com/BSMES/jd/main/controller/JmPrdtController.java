package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmMouldDTO;
import com.BSMES.jd.main.dto.JmPrdtDTO;
import com.BSMES.jd.main.service.JmPrdtService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 产品路由
 */
@RestController
@RequestMapping("/prdt")
public class JmPrdtController  {

    @Autowired
    JmPrdtService jmPrdtService;

    @GetMapping()
    public CommonReturn getPrdt(JmPrdtDTO dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmPrdtService.getPrdt(dto);
        }else{
            QueryWrapper queryWrapper = new QueryWrapper();
            result = jmPrdtService.getPrdtPage(dto,queryWrapper);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn savePrdt(@RequestBody JmPrdtDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmPrdtService.savePrdt(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editPrdt(@RequestBody JmPrdtDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmPrdtService.editPrdt(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delPrdt( String[] ids ){
        CommonReturn result = new CommonReturn();
        List<String> prdNos = java.util.Arrays.asList(ids);
        result = jmPrdtService.delPrdt(prdNos);
        return result;
    }

}
