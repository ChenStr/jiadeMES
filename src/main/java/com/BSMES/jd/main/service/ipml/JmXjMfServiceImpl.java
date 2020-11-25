package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmXjMfDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.*;
import com.BSMES.jd.main.service.*;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JmXjMfServiceImpl extends BaseServiceImpl<JmXjMfDao , JmXjMfEntity , JmXjMfDTO> implements JmXjMfService {

    @Autowired
    InssysvarService inssysvarService;

    @Autowired
    JmXj2TfService jmXj2TfService;

    @Autowired
    JmXj3TfService jmXj3TfService;

    @Autowired
    JmJobService jmJobService;

    @Autowired
    JmChkstdMfService jmChkstdMfService;

    @Autowired
    JmChkstdTfService jmChkstdTfService;

    @Autowired
    JmXjMfDao jmXjMfDao;

    @Override
    public void beforeInsert(JmXjMfDTO dto) {
        dto.setHpdate(new Date());
        dto.setSbuid("XJ");
    }

    @Override
    public void beforEedit(JmXjMfDTO dto) {

    }

    @DS("master")
    @Override
    public CommonReturn getXjMf(ResultType dto) {
        CommonReturn result = new CommonReturn();
//        List<JmXjMfDTO> xjMfs = this.select(this.getQueryWrapper(dto));
        List<JmXjMf2> xjMfs = jmXjMfDao.getJmXjMf2(dto);
        if(xjMfs.isEmpty()){
            result.setAll(20000,xjMfs,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,xjMfs,"查找成功");
        }
        return result;
    }

    @DS("master")
    @Override
    public synchronized CommonReturn getXjMfPlus(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmXjMf2> jmXjMfDTOS = jmXjMfDao.getJmXjMf2(dto);
        if (jmXjMfDTOS==null && jmXjMfDTOS.size()==0){
            JmXjMf2 jmXjMf2 = new JmXjMf2();
            //如果找不到数据的话那么就说明只有一条并且把他的模板数据带出来，如果没有那是真的没有
            if(dto.getPrdName()==null){
                result.setAll(20000,jmXjMfDTOS,"未查到数据");
//                return result;
            }else{
                //通过产品名称去检验标准表里去查询
                QueryWrapper<JmChkstdMfEntity> jmChkstdMfEntityQueryWrapper = new QueryWrapper<>();
                jmChkstdMfEntityQueryWrapper.eq("prd_name",dto.getPrdName());
                JmChkstdMfDTO jmChkstdMfDTO = jmChkstdMfService.selectOne(jmChkstdMfEntityQueryWrapper);
                //查询其表身数据
                QueryWrapper<JmChkstdTfEntity> jmChkstdTfEntityQueryWrapper = new QueryWrapper<>();
                jmChkstdTfEntityQueryWrapper.eq("chkstd_no",jmChkstdMfDTO.getChkstdNo());
                List<JmChkstdTfDTO> jmChkstdTfDTOS = jmChkstdTfService.select(jmChkstdTfEntityQueryWrapper);
                if (jmChkstdMfDTO!=null && jmChkstdTfDTOS!=null && jmChkstdMfDTO.getChkstdNo()!=null && jmChkstdTfDTOS.size()>0){
                    //将格式进行转换   将部门信息、产品信息带入即可
                    JmXjMfDTO jmXjMfDTO = new JmXjMfDTO();
                    jmXjMfDTO.setSorg(jmChkstdMfDTO.getSorg());
                    jmXjMfDTO.setPrdNo(jmChkstdMfDTO.getPrdNo());
                    jmXjMfDTO.setPrdName(jmXjMfDTO.getPrdName());
                    List<JmXjMf> jmXjMfs = new ArrayList<>();
                    for (int i = 0; i < jmChkstdTfDTOS.size() ; i++){
                        JmXjMf jmXjMf = new JmXjMf();
                        JmXj2TfDTO jmXj2TfDTO = new JmXj2TfDTO();
                        jmXj2TfDTO.setCid(jmChkstdTfDTOS.get(i).getCid());
                        jmXj2TfDTO.setChkPara(jmChkstdTfDTOS.get(i).getChkPara());
                        jmXj2TfDTO.setParaMin(jmChkstdTfDTOS.get(i).getParaMin());
                        jmXj2TfDTO.setParaMax(jmChkstdTfDTOS.get(i).getParaMax());
                        jmXj2TfDTO.setChkValue(new BigDecimal(jmChkstdTfDTOS.get(i).getChkPara()));
                        jmXjMfs.add(jmXjMf);
                    }
                    jmXjMf2.setJmXjMfDTO(jmXjMfDTO);
                    jmXjMf2.setJmXjMfs(jmXjMfs);
                    jmXjMfDTOS.add(jmXjMf2);
                }
                result.setAll(20000,jmXjMfDTOS,"查询到模板数据");
            }

        }else{
            //查询子表与其他信息
            //把所有要从数据库拿出来的东西先全部拿出来
            List<String> sids = new ArrayList<>();
            jmXjMfDTOS.stream().forEach(T->sids.add(T.getJmXjMfDTO().getSid()));
            //查询所有子表信息
            List<JmXj2TfDTO> jmXj2TfDTOS = new ArrayList<>();
            List<JmXj3TfDTO> jmXj3TfDTOS = new ArrayList<>();
            if(sids!=null && sids.size()>0){
                QueryWrapper<JmXj2TfEntity> jmXj2TfEntityQueryWrapper = new QueryWrapper<>();
                jmXj2TfEntityQueryWrapper.in("sid",sids);
                QueryWrapper<JmXj3TfEntity> jmXj3TfEntityQueryWrapper = new QueryWrapper<>();
                jmXj3TfEntityQueryWrapper.in("sid",sids);
                jmXj2TfDTOS = jmXj2TfService.select(jmXj2TfEntityQueryWrapper);
                jmXj3TfDTOS = jmXj3TfService.select(jmXj3TfEntityQueryWrapper);
            }
            //将数据打包成 一个2 多个3
            List<JmXjMf> jmXjMfs = new ArrayList<>();
            for (JmXj2TfDTO jmXj2TfDTO : jmXj2TfDTOS){
                JmXjMf jmXjMf = new JmXjMf();
                List<JmXj3TfDTO> jmXj3TfDTOS1 = new ArrayList<>();
                jmXjMf.setJmXj2TfDTO(jmXj2TfDTO);
                for (JmXj3TfDTO jmXj3TfDTO : jmXj3TfDTOS){
                    if (jmXj2TfDTO.getSid().equals(jmXj3TfDTO.getSid()) && jmXj2TfDTO.getCid().equals(jmXj3TfDTO.getCid())){
                        jmXj3TfDTOS1.add(jmXj3TfDTO);
                    }
                }
                jmXjMf.setJmXj3TfDTOS(jmXj3TfDTOS1);
                jmXjMfs.add(jmXjMf);
            }
            //将数据打包一个 主表 与多个子表
            for (JmXjMf2 jmXjMf2 : jmXjMfDTOS){
                List<JmXjMf> jmXjMfs1 = new ArrayList<>();
                for (JmXjMf jmXjMf : jmXjMfs){
                    if (jmXjMf2.getJmXjMfDTO().getSid().equals(jmXjMf.getJmXj2TfDTO().getSid())){
                        jmXjMfs1.add(jmXjMf);
                    }
                }
                jmXjMf2.setJmXjMfs(jmXjMfs1);
            }


        }

        result.setAll(20000,jmXjMfDTOS,"操作成功");
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn getXjMfdetailed(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmXjMf2> xjMfs = jmXjMfDao.getJmXjMf2detailed(dto);
        if(xjMfs.isEmpty()){
            result.setAll(20000,xjMfs,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,xjMfs,"查找成功");
        }
        return result;
    }

    @Transactional(rollbackFor=Exception.class)
    @DS("master")
    @Override
    public synchronized CommonReturn saveXjMf(JmXjMf2 dto) {
        CommonReturn result = new CommonReturn();
        Boolean flag = true;
        String sid = null;

        //首先判断是添加还是修改
        if (dto.getJmXjMfDTO()!=null && dto.getJmXjMfDTO().getSid()!=null){
            flag=false;
            sid = dto.getJmXjMfDTO().getSid();
            this.editXjMf(dto.getJmXjMfDTO());
        }else{
            sid = this.getKey("JmXjMf","sid",inssysvarService,dto.getJmXjMfDTO());
            dto.getJmXjMfDTO().setSid(sid);
            this.insert(dto.getJmXjMfDTO());
        }

        try{
            //如果是编辑的话
            if (flag==false){
                //删除所有原始数据
                QueryWrapper<JmXj2TfEntity> jmXj2TfEntityQueryWrapper = new QueryWrapper<>();
                jmXj2TfEntityQueryWrapper.eq("sid",dto.getJmXjMfDTO().getSid());
                QueryWrapper<JmXj3TfEntity> jmXj3TfEntityQueryWrapper = new QueryWrapper<>();
                jmXj3TfEntityQueryWrapper.eq("sid",dto.getJmXjMfDTO().getSid());
//                jmXj3TfService.remove(jmXj3TfEntityQueryWrapper);
//                jmXj2TfService.remove(jmXj2TfEntityQueryWrapper);
//                jmXj3TfService.deleteById(sid);
//                jmXj2TfService.deleteById(sid);
                jmXj3TfService.deleteXj3Tf(sid);
                jmXj2TfService.deleteXj2Tf(sid);
            }
            List<JmXj2TfDTO> jmXj2TfDTOS = new ArrayList<>();
            List<JmXj3TfDTO> jmXj3TfDTOS = new ArrayList<>();
            //将新的数据添加进去
            for (JmXjMf jmXjMf : dto.getJmXjMfs()){
                jmXjMf.getJmXj2TfDTO().setSid(sid);
                if (jmXjMf.getJmXj2TfDTO()!=null && jmXjMf.getJmXj2TfDTO().getSid()!=null){
//                    jmXj2TfService.saveXj2Tf(jmXjMf.getJmXj2TfDTO());
                    jmXj2TfDTOS.add(jmXjMf.getJmXj2TfDTO());
                }
                if (jmXjMf.getJmXj3TfDTOS()!=null && jmXjMf.getJmXj3TfDTOS().size()>0){
                    //将数值为空的进行筛选
                    for (JmXj3TfDTO jmXj3TfDTO : jmXjMf.getJmXj3TfDTOS()){
                        if (jmXj3TfDTO.getChkviation()==null){
                            jmXjMf.getJmXj3TfDTOS().remove(jmXj3TfDTO);
                        }
                    }
                    String sid1 = sid;
                    jmXjMf.getJmXj3TfDTOS().stream().forEach(t->t.setSid(sid1));
//                    jmXj3TfService.saveXj3Tfs(jmXjMf.getJmXj3TfDTOS());
                    jmXj3TfDTOS.addAll(jmXjMf.getJmXj3TfDTOS());
                }
            }
            jmXj2TfService.saveXj2Tfs(jmXj2TfDTOS);
            jmXj3TfService.saveXj3Tfs(jmXj3TfDTOS);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn saveXjMfs(List<JmXjMfDTO> dtos) {
        CommonReturn result = new CommonReturn();
        //将sid插入
        for(int i = 0 ; i < dtos.size() ; i++){
            //判定调度单号(制令单)是否存在 sid
            String key = this.getKey("JmXjMf","sid",inssysvarService,dtos.get(i));
            //首先先找到编码规则
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("sname","JmXjMf");
            String code = inssysvarService.selectOne(queryWrapper).getSbds();
            //获取括号前的数据
            String after = code.substring(code.indexOf('%')+1,code.length());
            String before = key.substring(0,key.length() - after.length());
            String keyafter = key.substring(key.length()-after.length(),key.length());
            key = before + MyUtils.geFourNumber(Integer.parseInt(keyafter)+i,after.length());
            dtos.get(i).setSid(key);
            //删除所有的 jb_no 的数据
            QueryWrapper<JmXjMfEntity> jmXjMfEntityQueryWrapper = new QueryWrapper<>();
            jmXjMfEntityQueryWrapper.eq("jb_no",dtos.get(i).getJbNo());
            this.remove(jmXjMfEntityQueryWrapper);
        }
        try{
            jmXjMfDao.saveJmXjMfs(dtos);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn editXjMf(JmXjMfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            //获取原先的人员属性值
            QueryWrapper<JmXjMfEntity> xjMfQueryWrapper = new QueryWrapper<>();
            xjMfQueryWrapper.eq("sid",dto.getSid());
            JmXjMfDTO worker = this.selectOne(xjMfQueryWrapper);
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

    @DS("master")
    @Override
    public CommonReturn delXjMf(List<String> sids) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmXjMfEntity> xjMfQueryWrapper = new QueryWrapper<>();
        QueryWrapper<JmXj2TfEntity> jmXj2TfEntityQueryWrapper = new QueryWrapper<>();
        QueryWrapper<JmXj3TfEntity> jmXj3TfEntityQueryWrapper = new QueryWrapper<>();
        xjMfQueryWrapper.in("sid",sids);
        jmXj2TfEntityQueryWrapper.eq("sid",sids);
        jmXj3TfEntityQueryWrapper.eq("sid",sids);
        List<JmXjMfDTO> jmXjMfDTOS = this.select(xjMfQueryWrapper);
        try{
            jmXj3TfService.remove(jmXj3TfEntityQueryWrapper);
            jmXj2TfService.remove(jmXj2TfEntityQueryWrapper);
            this.remove(xjMfQueryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn getXjMfPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        if (dto.getPage()==null){
            dto.setPage(1);
        }
        if (dto.getPageSize()==null){
            dto.setPageSize(10);
        }
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        List<JmXjMf2> data = (List<JmXjMf2>) this.getXjMf(dto).getData();
        PageInfo dataPages = new PageInfo<JmXjMf2>(data);
        result.setAll(20000,dataPages,"操作成功");
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn getXjMfPlusPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        if (dto.getPage()==null){
            dto.setPage(1);
        }
        if (dto.getPageSize()==null){
            dto.setPageSize(10);
        }
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        List<JmXjMf2> data = (List<JmXjMf2>) this.getXjMfPlus(dto).getData();
        PageInfo dataPages = new PageInfo<JmXjMf2>(data);
        result.setAll(20000,dataPages,"操作成功");
        return result;
    }

    @DS("master")
    @Scheduled(cron="0 30 7 * * ?")
    @Override
    public CommonReturn getXjTime() {
        CommonReturn result = new CommonReturn();
        result.setAll(20000,null,"无数据未修改");
        //查询出所有的首末检信息
        QueryWrapper<JmXjMfEntity> jmXjMfEntityQueryWrapper = new QueryWrapper<>();
        int[] ints = {1,2};
        jmXjMfEntityQueryWrapper.in("xjid",ints).eq("state",0);
        List<JmXjMfDTO> jmXjMfDTOS = this.select(jmXjMfEntityQueryWrapper);
        try{
            for (JmXjMfDTO jmXjMfDTO:jmXjMfDTOS){
                jmXjMfDTO.setState(6);
                this.edit(jmXjMfDTO);
            }
            result.setAll(20000,jmXjMfDTOS,"操作成功");
            System.out.println("巡检信息定时修改操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
            System.out.println("巡检信息定时修改操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getJmxjAttach(ResultType dto) {
        CommonReturn result = new CommonReturn();
        try{
            List<JmxjAttach> jmxjAttaches = jmXjMfDao.getJmxAttach(dto);
            result.setAll(20000,jmxjAttaches,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();

//        if(dto.getAscOrder()==null && dto.getDescOrder()==null){
//            dto.setDescOrder("hpdate");
//        }

        if (dto.getOtherIds()!=null && dto.getOtherIds().size()>0){
            queryWrapper.in("xjid",dto.getOtherIds());
        }
        if (MyUtils.StringIsNull(dto.getSid())){
            queryWrapper.like("sid",dto.getSid());
        }
        if (MyUtils.StringIsNull(dto.getSorg())){
            queryWrapper.eq("sorg",dto.getSorg());
        }
        if (MyUtils.StringIsNull(dto.getPrdName())){
            queryWrapper.like("prd_name",dto.getPrdName());
        }
        if (MyUtils.StringIsNull(dto.getMouldNo())){
            queryWrapper.eq("md_no",dto.getMouldNo());
        }
        if (dto.getBegDd()!=null){
            queryWrapper.ge("hpdate",dto.getBegDd());
        }
        if(dto.getEndDd()!=null){
            queryWrapper.le("hpdate",dto.getEndDd());
        }
        if (dto.getState()!=null){
            queryWrapper.eq("state",dto.getState());
        }
        if (MyUtils.StringIsNull(dto.getWkName())){
            queryWrapper.eq("wk_name",dto.getWkName());
        }
        if (MyUtils.StringIsNull(dto.getDevNo())){
            queryWrapper.eq("rs_no",dto.getDevNo());
        }
        if (MyUtils.StringIsNull(dto.getType())){
            queryWrapper.eq("jb_no",dto.getOtherId());
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
