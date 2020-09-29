package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.InsuserDTO;
import com.BSMES.jd.main.dto.JmMoMfDTO;
import com.BSMES.jd.main.dto.JobJoin;
import com.BSMES.jd.main.entity.InsorgEntity;
import com.BSMES.jd.main.entity.InsuserEntity;
import com.BSMES.jd.main.service.InsorgService;
import com.BSMES.jd.main.service.InsuserService;
import com.BSMES.jd.main.service.JmJobService;
import com.BSMES.jd.main.service.JmMoMfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequestMapping("/test")
@RestController
public class TestController {

    @Autowired
    InsuserService insuserService;

    @Autowired
    InsorgService insorgServicel;

    @Autowired
    JmMoMfService jmMoMfService;

    @Autowired
    JmJobService jmJobService;

    @GetMapping()
    public CommonReturn test(JobJoin jobJoins) {
        CommonReturn result = new CommonReturn();
        result = jmJobService.getJobJoinPage(jobJoins);
        return result;
    }

    @PostMapping()
    public Object posttest(@RequestBody JmMoMfDTO data){
        CommonReturn result = new CommonReturn();
        return data;
    }
}
