package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.InsuserDTO;
import com.BSMES.jd.main.entity.InsorgEntity;
import com.BSMES.jd.main.entity.InsuserEntity;
import com.BSMES.jd.main.service.InsuserService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    InsuserService insuserService;

    @GetMapping("/test")
    public CommonReturn test(InsuserDTO dto) throws Exception {
        CommonReturn result = new CommonReturn();
        Object test = MyUtils.createClassByString("com.BSMES.jd.main.entity.InsorgEntity");
        Class<?> clas = test.getClass();
        return result;
    }
}
