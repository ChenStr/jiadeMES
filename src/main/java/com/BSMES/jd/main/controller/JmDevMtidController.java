package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmDevMtidDTO;
import com.BSMES.jd.main.service.JmDevMtidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/jmdevmtid")
@RestController
public class JmDevMtidController {

    @Autowired
    JmDevMtidService jmDevMtidService;

    @GetMapping()
    public CommonReturn getJmDev(JmDevMtidDTO dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmDevMtidService.getDevMtid(dto);
        }else{
            result = jmDevMtidService.getDevMtidPage(dto);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveJmDev(@RequestBody JmDevMtidDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmDevMtidService.saveDevMtid(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editJmDev(@RequestBody JmDevMtidDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmDevMtidService.editDevMtid(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delJmDev( String[] devids,String[] mtIds ){
        CommonReturn result = new CommonReturn();
        List<String> devids1 = java.util.Arrays.asList(devids);
        List<String> mtIds1 = java.util.Arrays.asList(mtIds);
        result = jmDevMtidService.delDevMtid(devids1,mtIds1);
        return result;
    }

}
