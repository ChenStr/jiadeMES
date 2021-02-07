package com.BSMES.jd.main.service.ipml.erp;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.erp.ErpTfMoDao;
import com.BSMES.jd.main.dto.JmJobRecBDTO;
import com.BSMES.jd.main.dto.erp.ErpTfMoDTO;
import com.BSMES.jd.main.entity.JmChkstdTfEntity;
import com.BSMES.jd.main.entity.erp.ErpTfMoEntity;
import com.BSMES.jd.main.service.erp.ErpTfMoService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ErpTfMoServiceImpl extends BaseServiceImpl<ErpTfMoDao, ErpTfMoEntity, ErpTfMoDTO> implements ErpTfMoService {

    @Autowired
    ErpTfMoDao erpTfMoDao;

    @Override
    public void beforeInsert(ErpTfMoDTO dto) {

    }

    @Override
    public void beforEedit(ErpTfMoDTO dto) {

    }

    @DS("erp")
    @Override
    public CommonReturn saveTfMo(ErpTfMoDTO dto) {
        CommonReturn result = new CommonReturn();
        //判定是不是恶意调用
        if ("".equals(dto) && "".equals(dto.getMO_NO())){
            result.setAll(10001,null,"参数错误");
        }else{
            try{
                this.insert(dto);
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
    public CommonReturn saveTfMos(List<ErpTfMoDTO> dtos) {
        CommonReturn result = new CommonReturn();
        try{
            erpTfMoDao.insertTfMoS(dtos);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @DS("erp")
    @Override
    public CommonReturn editTfMo(ErpTfMoDTO dto) {
        CommonReturn result = new CommonReturn();
        try{
            this.edit(dto);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(10001,null,"操作失败");
        }
        return result;
    }

    @DS("erp")
    @Override
    public CommonReturn delTfMo(List<String> MONOS, List<Integer> ITMS) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<ErpTfMoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("MO_NO",MONOS);
        if (ITMS!=null && ITMS.size()>0){
            queryWrapper.in("ITM",ITMS);
        }
        try{
            if (MONOS!=null && MONOS.size()>0){
                this.remove(queryWrapper);
            }
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @DS("erp")
    @Override
    public CommonReturn exec(JmJobRecBDTO jmJobRecBDTO) {
        CommonReturn result = new CommonReturn();
        try{
            erpTfMoDao.exec(jmJobRecBDTO);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }
}
