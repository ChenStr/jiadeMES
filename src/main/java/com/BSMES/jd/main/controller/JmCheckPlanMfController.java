package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmCheckPlan;
import com.BSMES.jd.main.dto.JmCheckPlanMfDTO;
import com.BSMES.jd.main.dto.JmCheckPlanTfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.JmCheckPlanMfService;
import com.BSMES.jd.main.service.JmCheckPlanTfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cheackPlanMf")
public class JmCheckPlanMfController {

    @Autowired
    JmCheckPlanMfService jmCheckPlanMfService;

    @GetMapping()
    public CommonReturn getVar(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmCheckPlanMfService.getPlanMf(dto);
        }else{
            result = jmCheckPlanMfService.getPlanMfPage(dto);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveVar(@RequestBody JmCheckPlan dto){
        CommonReturn result = new CommonReturn();
        result = jmCheckPlanMfService.savePlanMf(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editVar(@RequestBody JmCheckPlanMfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmCheckPlanMfService.editPlanMf(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delVar( String[] sids){
        CommonReturn result = new CommonReturn();
        List<String> sids1 = java.util.Arrays.asList(sids);
        result = jmCheckPlanMfService.delPlanMf(sids1);
        return result;
    }

}
