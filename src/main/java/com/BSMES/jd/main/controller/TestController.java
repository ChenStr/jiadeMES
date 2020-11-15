package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dao.JmJobDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.dto.erp.ErpMfMoDTO;
import com.BSMES.jd.main.entity.InsorgEntity;
import com.BSMES.jd.main.entity.InsuserEntity;
import com.BSMES.jd.main.entity.JmPrdtEntity;
import com.BSMES.jd.main.entity.erp.ErpMfMoEntity;
import com.BSMES.jd.main.service.*;
import com.BSMES.jd.main.service.erp.ErpMfMoService;
import com.BSMES.jd.tools.my.MyUtils;
import com.BSMES.jd.tools.password.PasswordUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("/test")
@RestController
public class TestController {
    @Autowired
    JmPrdtService jmPrdtService;

    @Autowired
    ErpMfMoService erpMfMoService;

    @Autowired
    JmJobRecService jmJobRecService;

    @Autowired
    JmJobService jmJobService;

    @Autowired
    JmDevService jmDevService;


    @GetMapping()
    public CommonReturn test(ResultType dto) {
        CommonReturn result = new CommonReturn();
        String string = PasswordUtils.encode(dto.getSid());
        result.setAll(20000,string,"操作成功");
        return result;
    }

    @PostMapping()
    public Object posttest(@RequestBody JmMoMfDTO dto){
        CommonReturn result = new CommonReturn();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        ErpMfMoDTO erpMfMoDTO = new ErpMfMoDTO();
        erpMfMoDTO.setMO_NO(dto.getSid());
        erpMfMoDTO.setMO_DD(dateFormat.format(dto.getHpdate()));
        erpMfMoDTO.setSTA_DD(dateFormat.format(dto.getBegDd()));
        erpMfMoDTO.setEND_DD(dateFormat.format(dto.getEndDd()));
        erpMfMoDTO.setMRP_NO(dto.getPrdNo());
        erpMfMoDTO.setQTY(dto.getQty());
        erpMfMoDTO.setDEP(dto.getSorg());
        erpMfMoDTO.setCLOSE_ID(dto.getState().toString());
        erpMfMoDTO.setUSR(dto.getSmake());
        erpMfMoDTO.setCHK_MAN(dto.getChkMan());
        erpMfMoDTO.setCLS_DATE(dateFormat.format(dto.getHpdate()));
        erpMfMoDTO.setSYS_DATE(dateFormat.format(dto.getHpdate()));
        result.setAll(20000,erpMfMoDTO,"操作成功");
//        result = erpMfMoService.saveMfMo(erpMfMoDTO);
        return result;
    }

    @PutMapping()
    public Object puttest(@RequestBody JmMoMfDTO dto){
        CommonReturn result = new CommonReturn();
        ErpMfMoDTO erpMfMoDTO = new ErpMfMoDTO();
        erpMfMoDTO.setMO_NO(dto.getSid());
        if (dto.getQty()!=null){
            erpMfMoDTO.setQTY(dto.getQty());
        }
        if (dto.getState()!=null){
            erpMfMoDTO.setCLOSE_ID(dto.getState().toString());
        }
        result = erpMfMoService.editMfMo(erpMfMoDTO);
        return result;
    }
}
