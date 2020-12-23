package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmCheckPlanTfDTO;
import com.BSMES.jd.main.dto.JmGoodsCheckTfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.JmCheckPlanTfService;
import com.BSMES.jd.main.service.JmGoodsCheckTfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goodCheckTf")
public class JmGoodsCheckTfController {

    @Autowired
    JmGoodsCheckTfService jmGoodsCheckTfService;

    @GetMapping()
    public CommonReturn getVar(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmGoodsCheckTfService.getGoods(dto);
        }else{
            result = jmGoodsCheckTfService.getGoodsPage(dto);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveVar(@RequestBody List<JmGoodsCheckTfDTO> dtos){
        CommonReturn result = new CommonReturn();
        result = jmGoodsCheckTfService.saveGoods(dtos);
        return result;
    }

    @PutMapping()
    public CommonReturn editVar(@RequestBody JmGoodsCheckTfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmGoodsCheckTfService.editGoods(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delVar( String[] sids,Integer[] cids ){
        CommonReturn result = new CommonReturn();
        List<String> sids1 = java.util.Arrays.asList(sids);
        List<Integer> cids1 = java.util.Arrays.asList(cids);
        result = jmGoodsCheckTfService.delGoods(sids1,cids1);
        return result;
    }

}
