package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.InsgwcodeDTO;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.InsgwcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/insgwcode")
public class InsgwcodeController {

    @Autowired
    InsgwcodeService insgwcodeService;

    @GetMapping()
    public CommonReturn getVar(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = insgwcodeService.getInsgwcode(dto);
        }else{
            result = insgwcodeService.getInsgwcodePage(dto);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveVar(@RequestBody InsgwcodeDTO dto){
        CommonReturn result = new CommonReturn();
        result = insgwcodeService.saveInsgwcode(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editVar(@RequestBody InsgwcodeDTO dto){
        CommonReturn result = new CommonReturn();
        result = insgwcodeService.editInsgwcode(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delVar( String[] gwcodes ){
        CommonReturn result = new CommonReturn();
        List<String> gwcodes1 = java.util.Arrays.asList(gwcodes);
        result = insgwcodeService.delInsgwcode(gwcodes1);
        return result;
    }

}
