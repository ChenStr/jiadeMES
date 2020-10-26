package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmMtRecDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.*;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.main.service.JmMtRecBService;
import com.BSMES.jd.main.service.JmMtRecService;
import com.BSMES.jd.tools.ConvertUtils;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class JmMtRecServiceImpl extends BaseServiceImpl<JmMtRecDao, JmMtRecEntity, JmMtRecDTO> implements JmMtRecService {

    @Autowired
    InssysvarService inssysvarService;

    @Autowired
    JmMtRecBService jmMtRecBService;


    @Override
    public void beforeInsert(JmMtRecDTO dto) {
        dto.setHpdate(new Date());
    }

    @Override
    public void beforEedit(JmMtRecDTO dto) {

    }

    @Override
    public CommonReturn getMtRec(ResultType dto) {

        CommonReturn result = new CommonReturn();
        List<JmMtRec> jmMtRecs = new ArrayList<>();
        List<JmMtRecDTO> mts = this.select(getQueryWrapper(dto));
        if(mts.isEmpty()){
            result.setAll(20000,mts,"没有查找结果，建议仔细核对查找条件");
        }else{
            //将子表里的内容带出来
            for (JmMtRecDTO mt : mts){
                JmMtRec jmMtRec = new JmMtRec();
                QueryWrapper<JmMtRecBEntity> jmMtRecBEntityQueryWrapper = new QueryWrapper<>();
                jmMtRecBEntityQueryWrapper.eq("wx_no",mt.getWxNo());
                List<JmMtRecBDTO> jmMtRecBDTO = jmMtRecBService.select(jmMtRecBEntityQueryWrapper);
                jmMtRec.setJmMtRecDTO(mt);
                jmMtRec.setJmMtRecBDTOS(jmMtRecBDTO);
                jmMtRecs.add(jmMtRec);
            }
            result.setAll(20000,jmMtRecs,"查找成功");
        }
        return result;
    }

    @Transactional
    @Override
    public CommonReturn saveMtRec(JmMtRec dto) {
        CommonReturn result = new CommonReturn();
        Boolean flag = true;
        String sid = null;

        if (dto.getJmMtRecDTO()!=null && dto.getJmMtRecDTO().getWxNo()!=null){
            flag=false;
            sid = dto.getJmMtRecDTO().getWxNo();
            this.edit(dto.getJmMtRecDTO());
        }else{
            sid = this.getKey("Mt","wx_no",inssysvarService,dto.getJmMtRecDTO());
            dto.getJmMtRecDTO().setWxNo(sid);
            this.insert(dto.getJmMtRecDTO());
        }

        try{
            //如果是编辑的话
            if (flag==false){
                //删除所有原始数据
                QueryWrapper<JmMtRecBEntity> jmMtRecBEntityQueryWrapper = new QueryWrapper<>();
                jmMtRecBEntityQueryWrapper.eq("wx_no",sid);
                jmMtRecBService.remove(jmMtRecBEntityQueryWrapper);
            }
            //将sid补全
            if(dto.getJmMtRecBDTOS()!=null && dto.getJmMtRecBDTOS().size()>0){
                for (JmMtRecBDTO jmMtRecBDTO : dto.getJmMtRecBDTOS()){
                    jmMtRecBDTO.setWxNo(sid);
                }
                //将新的数据添加进去
                jmMtRecBService.saveMtRecBs(dto.getJmMtRecBDTOS());
            }



            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CommonReturn editMtRec(JmMtRecDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getMtId())){
            //获取原先的人员属性值
            QueryWrapper<JmMtRecEntity> mtRecQueryWrapper = new QueryWrapper<>();
            mtRecQueryWrapper.eq("wx_no",dto.getWxNo());
            JmMtRecDTO mt = this.selectOne(mtRecQueryWrapper);
            //设置用户不能操作的属性
            try{
                this.edit(dto);
                result.setAll(20000,null,"操作成功");
            }catch (Exception e){
                result.setAll(10001,null,"操作失败");
                e.printStackTrace();
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn delMtRec(List<String> wxNos) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmMtRecEntity> mtRecQueryWrapper = new QueryWrapper<>();
        QueryWrapper<JmMtRecBEntity> mtRecBEntityQueryWrapper = new QueryWrapper<>();
        mtRecQueryWrapper.in("wx_no",wxNos);
        mtRecBEntityQueryWrapper.in("wx_no",wxNos);
        try{
            this.remove(mtRecQueryWrapper);
            jmMtRecBService.remove(mtRecBEntityQueryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }


    @Override
    public CommonReturn getMtRecPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmMtRec> jmMtRecs = new ArrayList<>();
        IPage<JmMtRec> jmMtRecDTOs = this.selectPage(dto.getPage(),dto.getPageSize(),getQueryWrapper(dto));
        IPage<JmMtRecEntity> jmMtRecEntityIPage = this.selectPage(dto.getPage(),dto.getPageSize(),getQueryWrapper(dto));
        //将子表里的内容带出来
//        for (JmMtRec mt : jmMtRecDTOs.getRecords()){
//            JmMtRec jmMtRec = new JmMtRec();
//            QueryWrapper<JmMtRecBEntity> jmMtRecBEntityQueryWrapper = new QueryWrapper<>();
//            jmMtRecBEntityQueryWrapper.eq("wx_no",mt.getJmMtRecDTO().getWxNo());
//            List<JmMtRecBDTO> jmMtRecBDTO = jmMtRecBService.select(jmMtRecBEntityQueryWrapper);
//            jmMtRec.setJmMtRecDTO(mt.getJmMtRecDTO());
//            jmMtRec.setJmMtRecBDTOS(jmMtRecBDTO);
//            jmMtRecs.add(jmMtRec);
//        }
        for (JmMtRecEntity mt : jmMtRecEntityIPage.getRecords()){
            JmMtRec jmMtRec = new JmMtRec();
            QueryWrapper<JmMtRecBEntity> jmMtRecBEntityQueryWrapper = new QueryWrapper<>();
            jmMtRecBEntityQueryWrapper.eq("wx_no",mt.getWxNo());
            List<JmMtRecBDTO> jmMtRecBDTO = jmMtRecBService.select(jmMtRecBEntityQueryWrapper);
            jmMtRec.setJmMtRecDTO(ConvertUtils.convert(mt,currentDtoClass()));
            jmMtRec.setJmMtRecBDTOS(jmMtRecBDTO);
            jmMtRecs.add(jmMtRec);
        }
        jmMtRecDTOs.setRecords(jmMtRecs);
        if (jmMtRecDTOs==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmMtRecDTOs,"查找成功");
        }
        return result;
    }

    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();

        if(dto.getAscOrder()==null && dto.getDescOrder()==null){
            dto.setDescOrder("hpdate");
        }
        if (MyUtils.StringIsNull(dto.getDevNo())){
            queryWrapper.like("dev_no",dto.getDevNo());
        }
        if (MyUtils.StringIsNull(dto.getWkNo())){
            queryWrapper.eq("sal_no",dto.getWkNo());
        }
        if (MyUtils.StringIsNull(dto.getSorg())){
            queryWrapper.eq("dep",dto.getSorg());
        }
        if (MyUtils.StringIsNull(dto.getDep())){
            queryWrapper.like("sorg",dto.getDep());
        }
        if (dto.getBegDd()!=null){
            queryWrapper.ge("hpdate",dto.getBegDd());
        }
        if(dto.getEndDd()!=null){
            queryWrapper.le("hpdate",dto.getEndDd());
        }
        if (dto.getAscOrder()!=null){
            queryWrapper.orderByAsc(MyUtils.humpToLine((String) dto.getAscOrder()));
        }
        if (dto.getDescOrder()!=null && dto.getAscOrder()==null){
            queryWrapper.orderByDesc(MyUtils.humpToLine((String) dto.getDescOrder()));
        }

        return queryWrapper;
    }
}
