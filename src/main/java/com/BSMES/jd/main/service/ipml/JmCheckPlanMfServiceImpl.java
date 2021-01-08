package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmCheckPlanMfDao;
import com.BSMES.jd.main.dao.JmCheckPlanTfDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.*;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.main.service.JmCheckPlanMfService;
import com.BSMES.jd.main.service.JmCheckPlanTfService;
import com.BSMES.jd.tools.ConvertUtils;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListResourceBundle;

@Service
public class JmCheckPlanMfServiceImpl extends BaseServiceImpl<JmCheckPlanMfDao, JmCheckPlanMfEntity, JmCheckPlanMfDTO> implements JmCheckPlanMfService {

    @Autowired
    JmCheckPlanTfDao jmCheckPlanTfDao;

    @Autowired
    JmCheckPlanTfService jmCheckPlanTfService;

    @Autowired
    InssysvarService inssysvarService;

    @Override
    public void beforeInsert(JmCheckPlanMfDTO dto) {
        if (dto.getCreateTime()==null){
            dto.setCreateTime(new Date());
        }
    }

    @Override
    public void beforEedit(JmCheckPlanMfDTO dto) {

    }

    @Override
    public CommonReturn getPlanMf(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmCheckPlan> jmCheckPlans = new ArrayList<>();
        List<JmCheckPlanMfDTO> jmCheckPlanMfDTOS = this.select(this.getQueryWrapper(dto));
        if(jmCheckPlanMfDTOS.isEmpty()){
            result.setAll(20000,jmCheckPlanMfDTOS,"没有查找结果，建议仔细核对查找条件");
        }else{
            List<String> sids = new ArrayList<>();
            jmCheckPlanMfDTOS.stream().forEach(T->sids.add(T.getSid()));
            List<JmCheckPlanTfDTO> jmCheckPlanTfDTOS = new ArrayList<>();
            if(sids.size()>0) {
                QueryWrapper<JmCheckPlanTfEntity> jmCheckPlanTfEntityQueryWrapper = new QueryWrapper<>();
                jmCheckPlanTfEntityQueryWrapper.in("sid", sids);
                jmCheckPlanTfDTOS = jmCheckPlanTfService.select(jmCheckPlanTfEntityQueryWrapper);
            }
            if (jmCheckPlanTfDTOS.size()>0){

                for (JmCheckPlanMfDTO jmCheckPlanMfDTO : jmCheckPlanMfDTOS){
                    JmCheckPlan jmCheckPlan = new JmCheckPlan();
                    jmCheckPlan.setJmCheckPlanMfDTO(jmCheckPlanMfDTO);
                    List<JmCheckPlanTfDTO> jmCheckPlanTfDTOS1 = new ArrayList<>();
                    for (JmCheckPlanTfDTO jmCheckPlanTfDTO : jmCheckPlanTfDTOS){
                        if (jmCheckPlanMfDTO.getSid().equals(jmCheckPlanTfDTO.getSid())){
                            jmCheckPlanTfDTOS1.add(jmCheckPlanTfDTO);
                        }
                    }
                    jmCheckPlan.setJmCheckPlanTfDTOS(jmCheckPlanTfDTOS1);
                    jmCheckPlans.add(jmCheckPlan);
                }
            }
            result.setAll(20000,jmCheckPlans,"查找成功");
        }
        return result;
    }

    @Transactional
    @Override
    public CommonReturn savePlanMf(JmCheckPlan dto) {
        CommonReturn result = new CommonReturn();

        Boolean flag = true;
        String sid = null;
        if (dto.getJmCheckPlanMfDTO()!=null && dto.getJmCheckPlanMfDTO().getSid()!=null){
            flag=false;
            sid = dto.getJmCheckPlanMfDTO().getSid();
            this.edit(dto.getJmCheckPlanMfDTO());
        }else{
            sid = this.getKey("JmChkstd","sid",inssysvarService,dto.getJmCheckPlanMfDTO());
            dto.getJmCheckPlanMfDTO().setSid(sid);
            this.insert(dto.getJmCheckPlanMfDTO());
        }

        try{
            //如果是编辑的话
            if (flag==false){
                //删除所有原始数据
                QueryWrapper<JmCheckPlanTfEntity> jmCheckPlanTfEntityQueryWrapper = new QueryWrapper<>();
                jmCheckPlanTfEntityQueryWrapper.eq("sid",dto.getJmCheckPlanMfDTO().getSid());
                jmCheckPlanTfService.remove(jmCheckPlanTfEntityQueryWrapper);
            }
            //将新的数据添加进去
            for (JmCheckPlanTfDTO jmCheckPlanTfDTO : dto.getJmCheckPlanTfDTOS()){
                jmCheckPlanTfDTO.setSid(sid);
                if (jmCheckPlanTfDTO!=null && jmCheckPlanTfDTO.getSid()!=null){
                    jmCheckPlanTfService.savePlanTf(dto.getJmCheckPlanTfDTOS());
                }
            }
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public CommonReturn editPlanMf(JmCheckPlanMfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            //获取原先的人员属性值
            QueryWrapper<JmCheckPlanMfEntity> jmCheckPlanMfEntityQueryWrapper = new QueryWrapper<>();
            jmCheckPlanMfEntityQueryWrapper.eq("sid",dto.getSid());
            JmCheckPlanMfDTO jmCheckPlanMfDTO = this.selectOne(jmCheckPlanMfEntityQueryWrapper);
            //设置用户不能操作的属性
            try{
                this.edit(dto);
                result.setAll(20000,null,"操作成功");
            }catch (Exception e){
                result.setAll(10001,null,"操作失败");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn delPlanMf(List<String> sids) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmCheckPlanMfEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("sid",sids);
        QueryWrapper<JmCheckPlanTfEntity> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.in("sid",sids);
        try{
            this.remove(queryWrapper);
            jmCheckPlanTfService.remove(queryWrapper1);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001,null,"操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getPlanMfPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<JmCheckPlanMfEntity> jmCheckPlanMfEntityIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        IPage<JmCheckPlan> jmCheckPlanIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        List<JmCheckPlan> jmCheckPlans = new ArrayList<>();
        if (jmCheckPlanMfEntityIPage==null){
            result.setAll(10001,null,"参数错误");
        }else{
            List<String> sids = new ArrayList<>();

            jmCheckPlanMfEntityIPage.getRecords().stream().forEach(T->sids.add(T.getSid()));
            List<JmCheckPlanTfDTO> jmCheckPlanTfDTOS = new ArrayList<>();
            if(sids.size()>0) {
                QueryWrapper<JmCheckPlanTfEntity> jmCheckPlanTfEntityQueryWrapper = new QueryWrapper<>();
                jmCheckPlanTfEntityQueryWrapper.in("sid", sids);
                jmCheckPlanTfDTOS = jmCheckPlanTfService.select(jmCheckPlanTfEntityQueryWrapper);
            }
            if (jmCheckPlanTfDTOS.size()>0){
                for (JmCheckPlanMfEntity jmCheckPlanMf : jmCheckPlanMfEntityIPage.getRecords()){
                    JmCheckPlan jmCheckPlan = new JmCheckPlan();
                    jmCheckPlan.setJmCheckPlanMfDTO(ConvertUtils.convert(jmCheckPlanMf,currentDtoClass()));
                    List<JmCheckPlanTfDTO> jmCheckPlanTfDTOS1 = new ArrayList<>();
                    for (JmCheckPlanTfDTO jmCheckPlanTfDTO : jmCheckPlanTfDTOS){
                        if (jmCheckPlanMf.getSid().equals(jmCheckPlanTfDTO.getSid())){
                            jmCheckPlanTfDTOS1.add(jmCheckPlanTfDTO);
                        }
                    }
                    jmCheckPlan.setJmCheckPlanTfDTOS(jmCheckPlanTfDTOS1);
                    jmCheckPlans.add(jmCheckPlan);
                }
            }
            result.setAll(20000,jmCheckPlans,"查找成功");
        }
        return result;
    }

    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();

//        if(dto.getAscOrder()==null && dto.getDescOrder()==null){
//            dto.setDescOrder("sort");
//        }

        if (MyUtils.StringIsNull(dto.getSid())){
            queryWrapper.eq("sid",dto.getSid());
        }
        if (MyUtils.StringIsNull(dto.getOtherId())){
            queryWrapper.eq("name",dto.getOtherId());
        }
        if (MyUtils.StringIsNull(dto.getPrdNo())){
            queryWrapper.eq("prd_no",dto.getPrdNo());
        }
        if (MyUtils.StringIsNull(dto.getPrdName())){
            queryWrapper.eq("prd_name",dto.getPrdName());
        }
        if (MyUtils.StringIsNull(dto.getType())){
            queryWrapper.eq("check_type",dto.getType());
        }
        if (MyUtils.StringIsNull(dto.getOtherType())){
            queryWrapper.eq("prd_std",dto.getOtherType());
        }

        if (dto.getBegDd()!=null){
            queryWrapper.ge("create_time",dto.getBegDd());
        }
        if(dto.getEndDd()!=null){
            queryWrapper.le("create_time",dto.getEndDd());
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
