package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmMtdd;
import com.BSMES.jd.main.dto.JmMtddMfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.JmMtddMfService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mtddmf")
public class JmMtddMfController {

    @Autowired
    JmMtddMfService jmMtddMfService;

    @GetMapping()
    public CommonReturn getMtId(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmMtddMfService.getMtdd(dto);
        }else{
            QueryWrapper queryWrapper = new QueryWrapper();
            result = jmMtddMfService.getMtddPage(dto);
        }
        return result;
    }

    @GetMapping("/plus")
    public CommonReturn getMtddPlus(ResultType dto){
        CommonReturn result = new CommonReturn();
        result = jmMtddMfService.getMtddPlus(dto);
        return result;
    }

//    @GetMapping("/report")
//    public CommonReturn getMtddReport(JmMtddMfDTO dto){
//        CommonReturn result = new CommonReturn();
//        result = jmMtddMfService.getMtddReport(dto);
//        return result;
//    }

    @PostMapping()
    public CommonReturn saveMtId(@RequestBody JmMtdd dto){
        CommonReturn result = new CommonReturn();
        result = jmMtddMfService.saveMtdd(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editMtId(@RequestBody JmMtddMfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmMtddMfService.editMtdd(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delMtId( String[] sids ){
        CommonReturn result = new CommonReturn();
        List<String> sid1 = java.util.Arrays.asList(sids);
        result = jmMtddMfService.delMtdd(sid1);
        return result;
    }

}
