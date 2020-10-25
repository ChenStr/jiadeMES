package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmMdwxTfDao;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.JmChkstdMfDTO;
import com.BSMES.jd.main.dto.JmMdwxTfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.InsorgEntity;
import com.BSMES.jd.main.entity.JmMdwxTfEntity;
import com.BSMES.jd.main.service.JmMdwxTfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JmMdwxTfServiceImpl extends BaseServiceImpl<JmMdwxTfDao, JmMdwxTfEntity, JmMdwxTfDTO> implements JmMdwxTfService {

    @Autowired
    JmMdwxTfDao jmMdwxTfDao;

    @Override
    public void beforeInsert(JmMdwxTfDTO dto) {

    }

    @Override
    public void beforEedit(JmMdwxTfDTO dto) {

    }


    @Override
    public CommonReturn getJmMdwxTf(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmMdwxTfDTO> dtos = this.select(this.getQueryWrapper(dto));
        if(dtos.isEmpty()){
            result.setAll(20000,dtos,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,dtos,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn insertJmMdwxTfs(List<JmMdwxTfDTO> dtos) {
        CommonReturn result = new CommonReturn();
        try{
            jmMdwxTfDao.saveJmMdwxTfs(dtos);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CommonReturn editJmMdwxTf(JmMdwxTfDTO dto) {
        CommonReturn result = new CommonReturn();
        try {
            jmMdwxTfDao.editJmMdwxTf(dto);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }


    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();

//        if(dto.getAscOrder()==null && dto.getDescOrder()==null){
//            dto.setDescOrder("sort");
//        }

//        if (MyUtils.StringIsNull(dto.getSid())){
//            queryWrapper.like("orgcode",dto.getSid());
//        }
//        if (MyUtils.StringIsNull(dto.getDevName())){
//            queryWrapper.like("orgname",dto.getDevName());
//        }
//        if (MyUtils.StringIsNull(dto.getType())){
//            queryWrapper.eq("cattr",dto.getType());
//        }

//        if (dto.getAscOrder()!=null){
//            queryWrapper.orderByAsc(MyUtils.humpToLine((String) dto.getAscOrder()));
//        }
//        if (dto.getDescOrder()!=null && dto.getAscOrder()==null){
//            queryWrapper.orderByDesc(MyUtils.humpToLine((String) dto.getDescOrder()));
//        }


        return queryWrapper;
    }
}
