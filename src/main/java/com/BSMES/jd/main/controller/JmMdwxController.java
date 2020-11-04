package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmMdwx;
import com.BSMES.jd.main.dto.JmMdwxDTO;
import com.BSMES.jd.main.dto.JmMoMfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.JmMdwxService;
import com.BSMES.jd.main.service.JmMoMfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/jmmdwx")
@RestController
public class JmMdwxController {

    @Autowired
    JmMdwxService jmMdwxService;

    @GetMapping()
    public CommonReturn getJmMoMf(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmMdwxService.getJmMdwx(dto);
        }else{
            result = jmMdwxService.getJmMdwxPage(dto);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveJmMoMf(@RequestBody JmMdwx dto){
        CommonReturn result = new CommonReturn();
        result = jmMdwxService.saveJmMdwx(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editJmMoMf(@RequestBody JmMdwxDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmMdwxService.editJmMdwx(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delJmMoMf( String[] ids ){
        CommonReturn result = new CommonReturn();
        List<String> sid1 = java.util.Arrays.asList(ids);
        result = jmMdwxService.delJmMdwx(sid1);
        return result;
    }

}
