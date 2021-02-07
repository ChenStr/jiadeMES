package com.BSMES.jd.main.service.ipml.erp;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.erp.ErpMfMoDao;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.dto.erp.ErpMfMoDTO;
import com.BSMES.jd.main.entity.erp.ErpMfMoEntity;
import com.BSMES.jd.main.entity.erp.ErpTfMoEntity;
import com.BSMES.jd.main.service.erp.ErpMfMoService;
import com.BSMES.jd.main.service.erp.ErpTfMoService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.http.impl.cookie.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ErpMfMoServiceImpl extends BaseServiceImpl<ErpMfMoDao, ErpMfMoEntity, ErpMfMoDTO> implements ErpMfMoService {

    @Autowired
    ErpTfMoService erpTfMoService;

    @Autowired
    ErpMfMoService erpMfMoService;

    @Override
    public void beforeInsert(ErpMfMoDTO dto) {
        if ("".equals(dto.getCLOSE_ID())){
            dto.setCLOSE_ID("F");
        }
        if ("12".equals(dto.getCLOSE_ID()) || "94".equals(dto.getCLOSE_ID())){
            dto.setCLOSE_ID("T");
        }else{
            dto.setCLOSE_ID("F");
        }
        if ("".equals(dto.getUSR())  || StringUtils.isEmpty(dto.getUSR()) ){
            dto.setUSR("ADMIN");
        }
        if ("".equals(dto.getCHK_MAN()) || StringUtils.isEmpty(dto.getCHK_MAN())){
            dto.setCHK_MAN("ADMIN");
        }
        String str = dto.getMRP_NO()+"->";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        dto.setUNIT("1");
        dto.setML_BY_MM("T");
        dto.setREM("MES生产计划下达");
        dto.setID_NO(str);
        dto.setCF_ID("T");
        dto.setFIN_DD(dateFormat.format(new Date()));
        qtyiszero(dto);
    }

    @Override
    public void beforEedit(ErpMfMoDTO dto) {
        if ("12".equals(dto.getCLOSE_ID()) || "94".equals(dto.getCLOSE_ID())){
            dto.setCLOSE_ID("T");
        }else{
            dto.setCLOSE_ID("F");
        }
        qtyiszero(dto);
    }

    @DS("erp")
    @Override
    public CommonReturn getReturn(ResultType dto) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<ErpMfMoEntity> erpMfMoEntityQueryWrapper = new QueryWrapper<>();
        erpMfMoEntityQueryWrapper.eq("MO_NO",dto.getSid());
        List<ErpMfMoDTO> list = this.select(erpMfMoEntityQueryWrapper);
        result.setAll(20000,list,"操作成功");
        return result;
    }

    @DS("erp")
    @Override
    public CommonReturn saveMfMo(ErpMfMoDTO dto) {
        CommonReturn result = new CommonReturn();
        //判定是不是恶意调用
        if ("".equals(dto) && "".equals(dto.getMO_NO())){
            result.setAll(10001,null,"参数错误");
        }else{
            try{
                erpMfMoService.insert(dto);
                result.setAll(20000,null,"操作成功");
            }catch (Exception e){
                result.setAll(40000,null,"操作失败");
                e.printStackTrace();
            }
        }
        return result;
    }

    @DS("erp")
    @Override
    public CommonReturn editMfMo(ErpMfMoDTO dto) {
        CommonReturn result = new CommonReturn();
        if (dto==null && dto.getMO_NO()==null){
            result.setAll(10001,null,"参数错误");
        }else{
            try{
                erpMfMoService.edit(dto);
                result.setAll(20000,null,"操作成功");
            }catch (Exception e){
                result.setAll(40000,null,"操作失败");
                e.printStackTrace();
            }
        }
        return result;
    }

    @DS("erp")
    @Override
    public CommonReturn delMfMo(List<String> MONOS) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<ErpMfMoEntity> erpMfMoEntityQueryWrapper = new QueryWrapper<>();
        QueryWrapper<ErpTfMoEntity> erpTfMoEntityQueryWrapper = new QueryWrapper<>();
        erpMfMoEntityQueryWrapper.in("MO_NO",MONOS);
        erpTfMoEntityQueryWrapper.in("MO_NO",MONOS);
        try{
            erpTfMoService.delTfMo(MONOS,null);
            this.remove(erpMfMoEntityQueryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
            e.printStackTrace();
        }
        return result;
    }

    //新加同步判断逻辑
    public ErpMfMoDTO qtyiszero(ErpMfMoDTO dto){
        if ("".equals(dto.getQTY()) || dto.getQTY().equals(BigDecimal.ZERO)){
            dto.setCLOSE_ID("T");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
            dto.setFIN_DD(dateFormat.format(new Date()));
        }
        return dto;
    }
}
