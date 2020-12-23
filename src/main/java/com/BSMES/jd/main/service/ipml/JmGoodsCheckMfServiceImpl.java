package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmGoodsCheckMfDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.JmGoodsCheckMfEntity;
import com.BSMES.jd.main.entity.JmGoodsCheckTfEntity;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.main.service.JmGoodsCheckMfService;
import com.BSMES.jd.main.service.JmGoodsCheckTfService;
import com.BSMES.jd.tools.ConvertUtils;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JmGoodsCheckMfServiceImpl extends BaseServiceImpl<JmGoodsCheckMfDao, JmGoodsCheckMfEntity, JmGoodsCheckMfDTO> implements JmGoodsCheckMfService {

    @Autowired
    JmGoodsCheckTfService jmGoodsCheckTfService;


    @Autowired
    InssysvarService inssysvarService;


    @Override
    public void beforeInsert(JmGoodsCheckMfDTO dto) {
        if (dto.getCreateTime()==null){
            dto.setCreateTime(new Date());
        }
    }

    @Override
    public void beforEedit(JmGoodsCheckMfDTO dto) {

    }

    @Override
    public CommonReturn getGoods(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmGoodsCheck> jmGoodsChecks = new ArrayList<>();
        List<JmGoodsCheckMfDTO> jmGoodsCheckMfDTOS = this.select(this.getQueryWrapper(dto));
        if(jmGoodsCheckMfDTOS.isEmpty()){
            result.setAll(20000,jmGoodsCheckMfDTOS,"没有查找结果，建议仔细核对查找条件");
        }else{
            List<String> sids = new ArrayList<>();
            jmGoodsCheckMfDTOS.stream().forEach(T->sids.add(T.getSid()));
            List<JmGoodsCheckTfDTO> jmGoodsCheckTfDTOS = new ArrayList<>();
            if(sids.size()>0){
                QueryWrapper<JmGoodsCheckTfEntity> jmGoodsCheckTfEntityQueryWrapper = new QueryWrapper<>();
                jmGoodsCheckTfEntityQueryWrapper.in("sid",sids);
                jmGoodsCheckTfDTOS = jmGoodsCheckTfService.select(jmGoodsCheckTfEntityQueryWrapper);
            }
            if (jmGoodsCheckTfDTOS.size()>0){
                JmGoodsCheck jmGoodsCheck = new JmGoodsCheck();
                for (JmGoodsCheckMfDTO jmGoodsCheckMfDTO : jmGoodsCheckMfDTOS){
                    jmGoodsCheck.setJmGoodsCheckMfDTO(jmGoodsCheckMfDTO);
                    List<JmGoodsCheckTfDTO> jmGoodsCheckTfDTOS1 = new ArrayList<>();
                    for (JmGoodsCheckTfDTO jmGoodsCheckTfDTO : jmGoodsCheckTfDTOS){
                        if (jmGoodsCheckMfDTO.getSid().equals(jmGoodsCheckTfDTO.getSid())){
                            jmGoodsCheckTfDTOS1.add(jmGoodsCheckTfDTO);
                        }
                    }
                    jmGoodsCheck.setJmGoodsCheckTfDTOS(jmGoodsCheckTfDTOS1);
                    jmGoodsChecks.add(jmGoodsCheck);
                }
            }
            result.setAll(20000,jmGoodsChecks,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveGoods(JmGoodsCheck dto) {
        CommonReturn result = new CommonReturn();

        Boolean flag = true;
        String sid = null;
        if (dto.getJmGoodsCheckMfDTO()!=null && dto.getJmGoodsCheckMfDTO().getSid()!=null){
            flag=false;
            sid = dto.getJmGoodsCheckMfDTO().getSid();
            this.edit(dto.getJmGoodsCheckMfDTO());
        }else{
            sid = this.getKey("JmChkstd","sid",inssysvarService,dto.getJmGoodsCheckMfDTO());
            dto.getJmGoodsCheckMfDTO().setSid(sid);
            this.insert(dto.getJmGoodsCheckMfDTO());
        }

        try{
            //如果是编辑的话
            if (flag==false){
                //删除所有原始数据
                QueryWrapper<JmGoodsCheckTfEntity> jmGoodsCheckTfEntityQueryWrapper = new QueryWrapper<>();
                jmGoodsCheckTfEntityQueryWrapper.eq("sid",dto.getJmGoodsCheckMfDTO().getSid());
                jmGoodsCheckTfService.remove(jmGoodsCheckTfEntityQueryWrapper);
            }
            //将新的数据添加进去
            for (JmGoodsCheckTfDTO jmGoodsCheckTfDTO : dto.getJmGoodsCheckTfDTOS()){
                jmGoodsCheckTfDTO.setSid(sid);
                if (jmGoodsCheckTfDTO!=null && jmGoodsCheckTfDTO.getSid()!=null){
                    jmGoodsCheckTfService.saveGoods(dto.getJmGoodsCheckTfDTOS());
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
    public CommonReturn editGoods(JmGoodsCheckMfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            //获取原先的人员属性值
            QueryWrapper<JmGoodsCheckMfEntity> jmGoodsCheckMfEntityQueryWrapper = new QueryWrapper<>();
            jmGoodsCheckMfEntityQueryWrapper.eq("sid",dto.getSid());
            JmGoodsCheckMfDTO jmGoodsCheckMfDTO = this.selectOne(jmGoodsCheckMfEntityQueryWrapper);
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
    public CommonReturn delGoods(List<String> sids) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmGoodsCheckMfEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("sid",sids);
        QueryWrapper<JmGoodsCheckTfEntity> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.in("sid",sids);
        try{
            this.remove(queryWrapper);
            jmGoodsCheckTfService.remove(queryWrapper1);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001,null,"操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getGoodsPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<JmGoodsCheckMfEntity> jmGoodsCheckMfEntityIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        IPage<JmGoodsCheck> jmGoodsCheckIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        List<JmGoodsCheck> jmGoodsChecks = new ArrayList<>();
        if (jmGoodsCheckMfEntityIPage==null){
            result.setAll(10001,null,"参数错误");
        }else{
            List<String> sids = new ArrayList<>();

            jmGoodsCheckMfEntityIPage.getRecords().stream().forEach(T->sids.add(T.getSid()));
            List<JmGoodsCheckTfDTO> jmGoodsCheckTfDTOS = new ArrayList<>();

            if(sids.size()>0){
                QueryWrapper<JmGoodsCheckTfEntity> jmGoodsCheckTfEntityQueryWrapper = new QueryWrapper<>();
                jmGoodsCheckTfEntityQueryWrapper.in("sid",sids);
                jmGoodsCheckTfDTOS = jmGoodsCheckTfService.select(jmGoodsCheckTfEntityQueryWrapper);
            }
            if (jmGoodsCheckTfDTOS.size()>0){
                JmGoodsCheck jmGoodsCheck = new JmGoodsCheck();
                for (JmGoodsCheckMfEntity jmGoodsCheckMfEntity : jmGoodsCheckMfEntityIPage.getRecords()){
                    jmGoodsCheck.setJmGoodsCheckMfDTO(ConvertUtils.convert(jmGoodsCheckMfEntity,currentDtoClass()));
                    List<JmGoodsCheckTfDTO> jmGoodsCheckTfDTOS1 = new ArrayList<>();
                    for (JmGoodsCheckTfDTO jmGoodsCheckTfDTO : jmGoodsCheckTfDTOS){
                        if (jmGoodsCheckMfEntity.getSid().equals(jmGoodsCheckTfDTO.getSid())){
                            jmGoodsCheckTfDTOS1.add(jmGoodsCheckTfDTO);
                        }
                    }
                    jmGoodsCheck.setJmGoodsCheckTfDTOS(jmGoodsCheckTfDTOS1);
                    jmGoodsChecks.add(jmGoodsCheck);
                }
            }
            result.setAll(20000,jmGoodsChecks,"查找成功");
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
        if (MyUtils.StringIsNull(dto.getDep())){
            queryWrapper.eq("supply_id",dto.getDep());
        }
        if (MyUtils.StringIsNull(dto.getSorg())){
            queryWrapper.eq("supply_name",dto.getSorg());
        }
        if (MyUtils.StringIsNull(dto.getType())){
            queryWrapper.eq("raw_spc",dto.getType());
        }
        if (MyUtils.StringIsNull(dto.getPrdNo())){
            queryWrapper.eq("prd_no",dto.getPrdNo());
        }
        if (MyUtils.StringIsNull(dto.getPrdName())){
            queryWrapper.eq("prd_name",dto.getPrdName());
        }
        if (MyUtils.StringIsNull(dto.getOtherId())){
            queryWrapper.eq("check_no",dto.getOtherId());
        }
        if (MyUtils.StringIsNull(dto.getOtherType())){
            queryWrapper.eq("spc",dto.getOtherType());
        }
        if (MyUtils.StringIsNull(dto.getDevid())){
            queryWrapper.eq("spot_plan",dto.getDevid());
        }
        if (MyUtils.StringIsNull(dto.getMouldNo())){
            queryWrapper.eq("check_conclu",dto.getMouldNo());
        }
        if (MyUtils.StringIsNull(dto.getMouldName())){
            queryWrapper.eq("lost_opinion",dto.getMouldName());
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
