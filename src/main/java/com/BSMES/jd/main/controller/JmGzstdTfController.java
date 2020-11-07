package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmGzstd;
import com.BSMES.jd.main.dto.JmGzstdMfDTO;
import com.BSMES.jd.main.dto.JmGzstdTfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.JmGzstdMfService;
import com.BSMES.jd.main.service.JmGzstdTfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jmgzstdtf")
public class JmGzstdTfController {

    @Autowired
    JmGzstdTfService jmGzstdTfService;

    @GetMapping()
    public CommonReturn getVar(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmGzstdTfService.getGzstd(dto);
        }else{
            result = jmGzstdTfService.getGzstdPage(dto);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveVar(@RequestBody List<JmGzstdTfDTO> dto){
        CommonReturn result = new CommonReturn();
        result = jmGzstdTfService.saveGzstds(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editVar(@RequestBody JmGzstdTfDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmGzstdTfService.editGzstd(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delVar( String[] gzstdNos,Integer[] cids ){
        CommonReturn result = new CommonReturn();
        List<String> gzstdNos1 = java.util.Arrays.asList(gzstdNos);
        List<Integer> cids1 = java.util.Arrays.asList(cids);
        result = jmGzstdTfService.delGzstd(gzstdNos1,cids1);
        return result;
    }

}
