package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dao.JmJobDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.InsorgEntity;
import com.BSMES.jd.main.entity.InsuserEntity;
import com.BSMES.jd.main.service.*;
import com.BSMES.jd.tools.my.MyUtils;
import com.BSMES.jd.tools.password.PasswordUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequestMapping("/test")
@RestController
public class TestController {
    @Autowired
    JmMoMfService service;


    @GetMapping()
    public CommonReturn test(ResultType dto) {
        CommonReturn result = new CommonReturn();
        result = service.getMoNo(dto);
        return result;
    }

    @PostMapping()
    public Object posttest(@RequestBody JmMoMfDTO data){
        CommonReturn result = new CommonReturn();
        return data;
    }
}
