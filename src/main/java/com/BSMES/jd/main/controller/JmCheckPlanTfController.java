package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmCheckItemDTO;
import com.BSMES.jd.main.dto.JmCheckPlanTfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.JmCheckItemService;
import com.BSMES.jd.main.service.JmCheckPlanTfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cheackPlanTf")
public class JmCheckPlanTfController {

    @Autowired
    JmCheckPlanTfService jmCheckPlanTfService;

    @GetMapping()
    public CommonReturn getVar(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmCheckPlanTfService.getPlanTf(dto);
        }else{
            result = jmCheckPlanTfService.getPlanTfPage(dto);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveVar(@RequestBody List<JmCheckPlanTfDTO> dtos){
        CommonReturn result = new CommonReturn();
        result = jmCheckPlanTfService.savePlanTf(dtos);
        return result;
    }

    @PutMapping()
    public CommonReturn editVar(@RequestBody JmCheckPlanTfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmCheckPlanTfService.editPlanTf(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delVar( String[] sids,Integer[] cids ){
        CommonReturn result = new CommonReturn();
        List<String> sids1 = java.util.Arrays.asList(sids);
        List<Integer> cids1 = java.util.Arrays.asList(cids);
        result = jmCheckPlanTfService.delPlanTf(sids1,cids1);
        return result;
    }

}
