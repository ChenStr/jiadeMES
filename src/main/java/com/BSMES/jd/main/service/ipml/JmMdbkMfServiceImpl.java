package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmMdbkMfDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.*;
import com.BSMES.jd.main.service.*;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class JmMdbkMfServiceImpl extends BaseServiceImpl<JmMdbkMfDao, JmMdbkMfEntity, JmMdbkMfDTO> implements JmMdbkMfService {

    @Autowired
    InssysvarService inssysvarService;

    @Autowired
    JmMdbkTfService jmMdbkTfService;

    @Autowired
    JmMouldService jmMouldService;

    @Autowired
    JmMdlyTfService jmMdlyTfService;

    @Autowired
    JmJobRecService jmJobRecService;

    @Autowired
    JmJobRecBService jmJobRecBService;

    @Override
    public void beforeInsert(JmMdbkMfDTO dto) {

    }

    @Override
    public void beforEedit(JmMdbkMfDTO dto) {

    }

    @DS("master")
    @Override
    public CommonReturn getMdbkMf(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmMdbkMfDTO> sorgs = this.select(this.getQueryWrapper(dto));
        if(sorgs.isEmpty()){
            result.setAll(20000,sorgs,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,sorgs,"查找成功");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn saveMdbk(JmMdbk dto) {
        CommonReturn result = new CommonReturn();
        Boolean flag = true;
        String sid = null;

        //首先判断是添加还是修改
        if (dto.getJmMdbkMfDTO()!=null && dto.getJmMdbkMfDTO().getSid()!=null){
            flag=false;
            sid = dto.getJmMdbkMfDTO().getSid();
            this.editMdbkMf(dto.getJmMdbkMfDTO());
        }else{
            sid = this.getKey("Mdbk","sid",inssysvarService,dto.getJmMdbkMfDTO());
            dto.getJmMdbkMfDTO().setSid(sid);
            this.insert(dto.getJmMdbkMfDTO());
        }

        try{
            //如果是编辑的话
            if (flag==false){
                //删除所有原始数据
                QueryWrapper<JmMdbkTfEntity> jmMdbkTfEntityQueryWrapper = new QueryWrapper<>();
                jmMdbkTfEntityQueryWrapper.eq("sid",sid);
                jmMdbkTfService.remove(jmMdbkTfEntityQueryWrapper);
            }
            //将sid补全
            if(dto.getJmMdbkTfDTOS()!=null && dto.getJmMdbkTfDTOS().size()>0){
                for (JmMdbkTfDTO jmMdbkTfDTO : dto.getJmMdbkTfDTOS()){
                    jmMdbkTfDTO.setSid(sid);
                    //修改模具状态
                    JmMouldDTO jmMouldDTO = new JmMouldDTO();
                    jmMouldDTO.setMdNo(jmMdbkTfDTO.getMdNo());
                    jmMouldDTO.setTypeid(1);
                    jmMouldDTO.setState(dto.getJmMdbkMfDTO().getState().toString());
                    jmMouldService.editMould(jmMouldDTO);
                }
                //将新的数据添加进去
                jmMdbkTfService.saveMdbkTfs(dto.getJmMdbkTfDTOS());
            }



            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }

        return result;
    }

    @DS("master")
    @Override
    public CommonReturn saveMdbkMf(JmMdbkMfDTO dto) {
        CommonReturn result = new CommonReturn();
        if (dto.getSid()==null){
            dto.setSid(getKey("Mdbk","sid",inssysvarService,dto));
        }
        //判断dto是否为空 判断dto的usrcode是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            QueryWrapper<JmMdbkMfEntity> jmMdbkMfEntityQueryWrapper = new QueryWrapper<>();
            jmMdbkMfEntityQueryWrapper.eq("sid",dto.getSid());
            JmMdbkMfDTO jmMdbkMfDTO = this.selectOne(jmMdbkMfEntityQueryWrapper);
            //判断 usrcode 是否重复
            if (jmMdbkMfDTO==null || jmMdbkMfDTO.getSid()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"模具还回单号已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn editMdbkMf(JmMdbkMfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            //获取原先的用户属性值
            QueryWrapper<JmMdbkMfEntity> jmMdbkMfEntityQueryWrapper = new QueryWrapper<>();
            jmMdbkMfEntityQueryWrapper.eq("sid",dto.getSid());
            JmMdbkMfDTO var = this.selectOne(jmMdbkMfEntityQueryWrapper);
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

    @DS("master")
    @Override
    public CommonReturn delMdbkMf(List<String> sids) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmMdbkMfEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("sid",sids);
        try{
            this.remove(queryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getDay(ResultType dto) {
        CommonReturn result = new CommonReturn();
        //判断模具号是否为空
        if (dto!=null && dto.getMouldNo()!=null){
            //根据模具来查找出库日期
            BigDecimal sum = new BigDecimal("0");
            QueryWrapper<JmMdlyTfEntity> jmMdlyTfEntityQueryWrapper = new QueryWrapper<>();
            jmMdlyTfEntityQueryWrapper.eq("md_no",dto.getMouldNo()).orderByDesc("mdly_dd");
            List<JmMdlyTfDTO> jmMdlyTfDTOS = jmMdlyTfService.select(jmMdlyTfEntityQueryWrapper);
            JmMdlyTfDTO jmMdlyTfDTO = new JmMdlyTfDTO();
            if (jmMdlyTfDTOS!=null && jmMdlyTfDTOS.size()>0){
                jmMdlyTfDTO = jmMdlyTfDTOS.get(0);
            }
            //查询其所有的随工单
            QueryWrapper<JmJobRecEntity> jmJobRecEntityQueryWrapper = new QueryWrapper<>();
            jmJobRecEntityQueryWrapper.eq("md_no",dto.getMouldNo()).ge("op_dd",jmMdlyTfDTO.getMdlyDd());
            List<JmJobRecDTO> jmJobRecDTOS = jmJobRecService.select(jmJobRecEntityQueryWrapper);
            List<JmJobRecBDTO> jmJobRecBDTOS = new ArrayList<>();
            List<String> sids = new ArrayList<>();
            if (jmJobRecDTOS!=null && jmJobRecDTOS.size()>0){
                for (JmJobRecDTO jmJobRecDTO:jmJobRecDTOS){
                    sids.add(jmJobRecDTO.getOpsid());
                }
            }
            if (sids.size()>0){
                QueryWrapper<JmJobRecBEntity> jmJobRecBEntityQueryWrapper = new QueryWrapper<>();
                jmJobRecBEntityQueryWrapper.in("opsid",sids);
                jmJobRecBDTOS = jmJobRecBService.select(jmJobRecBEntityQueryWrapper);
            }
            if (jmJobRecBDTOS.size()>0){
                for (JmJobRecBDTO jmJobRecBDTO:jmJobRecBDTOS){
                    if(jmJobRecBDTO.getQtyOk()!=null){
                        sum = sum.add(jmJobRecBDTO.getQtyOk());
                    }
                }
                result.setAll(20000,sum,"操作成功");
            }else{
                result.setAll(20000,sum,"操作失败");
            }


        }else{
            result.setAll(40000,null,"参数错误");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn getMdbkMfPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<JmMdbkMfDTO> jmMdbkMfDTOIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        if (jmMdbkMfDTOIPage==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmMdbkMfDTOIPage,"查找成功");
        }
        return result;
    }

    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();

//        if(dto.getAscOrder()==null && dto.getDescOrder()==null){
//            dto.setDescOrder("sort");
//        }

        if (MyUtils.StringIsNull(dto.getSid())){
            queryWrapper.like("sid",dto.getSid());
        }
        if (MyUtils.StringIsNull(dto.getSorg())){
            queryWrapper.like("dep",dto.getSorg());
        }
        if (MyUtils.StringIsNull(dto.getOtherId())){
            queryWrapper.eq("jb_no",dto.getOtherId());
        }
        if (dto.getState()!=null){
            queryWrapper.eq("state",dto.getState());
        }
        if (dto.getSorg()!=null){
            queryWrapper.eq("sorg",dto.getSorg());
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
