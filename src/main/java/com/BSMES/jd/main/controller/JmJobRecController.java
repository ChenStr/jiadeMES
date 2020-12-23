package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.service.JmJobRecService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/jobrec")
public class JmJobRecController {

    @Autowired
    JmJobRecService jmJobRecService;

    @GetMapping("")
    public CommonReturn getJobRec(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmJobRecService.getJobRec(dto);
        }else{
            result = jmJobRecService.getJobRecPage(dto);
        }
        return result;
    }

    @GetMapping("/plus")
    public CommonReturn getRecs(JobRec dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmJobRecService.getJobRecs(dto);
        }
        else{
            result = jmJobRecService.getJobRecsPage(dto);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveJobRec(@RequestBody JobRecSave jobRecSave){
        CommonReturn result = new CommonReturn();
        result = jmJobRecService.saveJobRecAndRecB(jobRecSave);
        return result;
    }

    @PutMapping()
    public CommonReturn editJobRec(@RequestBody JmJobRecDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmJobRecService.editJobRec(dto);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delJobRec( String[] opsids ){
        CommonReturn result = new CommonReturn();
        List<String> opsids1 = java.util.Arrays.asList(opsids);
        result = jmJobRecService.delJobRec(opsids1);
        return result;
    }

    @GetMapping("/taskeditJobRec")
    public CommonReturn taskeditJobRec(){
        CommonReturn result = new CommonReturn();

        result = jmJobRecService.taskeditJobRec();

        return result;
    }

    @GetMapping("/depMother")
    public CommonReturn depMother(ResultType dto) throws IOException, ParseException {
        CommonReturn result = new CommonReturn();

        result = jmJobRecService.getDepMoth(dto);

        return result;
    }
}
