package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmBsDictionaryDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.JmBsDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典表
 */
@RestController
@RequestMapping("/dictionary")
public class JmBsDictionaryController {

    @Autowired
    JmBsDictionaryService jmBsDictionaryService;

    @GetMapping()
    public CommonReturn getVar(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmBsDictionaryService.getDictionary(dto);
        }else{
            result = jmBsDictionaryService.getDictionaryPage(dto);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveVar(@RequestBody JmBsDictionaryDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmBsDictionaryService.saveDictionary(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editVar(@RequestBody JmBsDictionaryDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmBsDictionaryService.editDictionary(dto);
        return result;
    }

    @DeleteMapping("/false")
    public CommonReturn delFalse(JmBsDictionaryDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmBsDictionaryService.delfalseDictionary(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delVar( String[] ids ){
        CommonReturn result = new CommonReturn();
        List<String> codes1 = java.util.Arrays.asList(ids);
        result = jmBsDictionaryService.delDictionary(codes1);
        return result;
    }

}
