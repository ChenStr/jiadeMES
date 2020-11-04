package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmMdwxDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.*;
import com.BSMES.jd.main.service.*;
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

@Service
public class JmMdwxServiceImpl extends BaseServiceImpl<JmMdwxDao, JmMdwxEntity, JmMdwxDTO> implements JmMdwxService {

    @Autowired
    JmMdwxTfService jmMdwxTfService;

    @Autowired
    InssysvarService inssysvarService;

    @Autowired
    JmMouldService jmMouldService;

    @Autowired
    JmBsDictionaryService jmBsDictionaryService;


    @Override
    public void beforeInsert(JmMdwxDTO dto) {
        dto.setHpdate(new Date());
        QueryWrapper<JmBsDictionaryEntity> jmBsDictionaryEntityQueryWrapper = new QueryWrapper<>();
        jmBsDictionaryEntityQueryWrapper.eq("id","DIS20201030027");
        JmBsDictionaryDTO jmBsDictionaryDTO = jmBsDictionaryService.selectOne(jmBsDictionaryEntityQueryWrapper);
        dto.setState(Integer.valueOf(jmBsDictionaryDTO.getCode()));
    }

    @Override
    public void beforEedit(JmMdwxDTO dto) {

    }


    @Override
    public CommonReturn getJmMdwx(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmMdwx> jmMdwxes = new ArrayList<>();
        List<JmMdwxDTO> mdwx = this.select(getQueryWrapper(dto));
        if(mdwx.isEmpty()){
            result.setAll(20000,mdwx,"没有查找结果，建议仔细核对查找条件");
        }else{
            //将子表里的内容带出来
            for (JmMdwxDTO md : mdwx){
                JmMdwx jmMdwx = new JmMdwx();
                QueryWrapper<JmMdwxTfEntity> jmMdwxTfEntityQueryWrapper = new QueryWrapper<>();
                jmMdwxTfEntityQueryWrapper.eq("sid",md.getSid());
                List<JmMdwxTfDTO> jmMdwxTfDTOS = jmMdwxTfService.select(jmMdwxTfEntityQueryWrapper);
                jmMdwx.setJmMdwxDTO(md);
                jmMdwx.setJmMdwxTfDTOs(jmMdwxTfDTOS);
                //查询模具名称
                QueryWrapper<JmMouldEntity> jmMouldEntityQueryWrapper = new QueryWrapper<>();
                jmMouldEntityQueryWrapper.eq("md_no",md.getMdNo());
                JmMouldDTO jmMouldDTO = jmMouldService.selectOne(jmMouldEntityQueryWrapper);
                jmMdwx.setJmMouldDTO(jmMouldDTO);
                jmMdwxes.add(jmMdwx);

            }
            result.setAll(20000,jmMdwxes,"查找成功");
        }
        return result;
    }

    @Transactional
    @Override
    public CommonReturn saveJmMdwx(JmMdwx dto) {
        CommonReturn result = new CommonReturn();
        Boolean flag = true;
        String sid = null;

        //首先判断是添加还是修改
        if (dto.getJmMdwxDTO()!=null && dto.getJmMdwxDTO().getSid()!=null){
            flag=false;
            sid = dto.getJmMdwxDTO().getSid();
            this.edit(dto.getJmMdwxDTO());
        }else{
            sid = this.getKey("JmMdwx","sid",inssysvarService,dto.getJmMdwxDTO());
            dto.getJmMdwxDTO().setSid(sid);
            this.insert(dto.getJmMdwxDTO());
        }

        try{

            //如果是编辑的话先进行删除
            if (flag==false){
                //删除所有的原始数据
                QueryWrapper<JmMdwxTfEntity> jmMdwxTfEntityQueryWrapper = new QueryWrapper<>();
                jmMdwxTfEntityQueryWrapper.eq("sid",sid);
                jmMdwxTfService.remove(jmMdwxTfEntityQueryWrapper);
            }
            //将新的数据新增进去
            if(dto.getJmMdwxTfDTOs()!=null && dto.getJmMdwxTfDTOs().size()>0){
                //将sid填入
                for(JmMdwxTfDTO jmMdwxTfDTO : dto.getJmMdwxTfDTOs()){
                    jmMdwxTfDTO.setSid(sid);
                }
                jmMdwxTfService.insertJmMdwxTfs(dto.getJmMdwxTfDTOs());
            }
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public CommonReturn editJmMdwx(JmMdwxDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            //获取原先的用户属性值
            QueryWrapper<JmMdwxEntity> sorgqueryWrapper = new QueryWrapper<>();
            sorgqueryWrapper.eq("sid",dto.getSid());
            JmMdwxDTO var = this.selectOne(sorgqueryWrapper);
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
    public CommonReturn delJmMdwx(List<String> sid) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmMdwxEntity> jmMdwxEntityQueryWrapper = new QueryWrapper<>();
        QueryWrapper<JmMdwxTfEntity> jmMdwxTfEntityQueryWrapper = new QueryWrapper<>();
        jmMdwxEntityQueryWrapper.in("sid",sid);
        jmMdwxTfEntityQueryWrapper.in("sid",sid);
        try{
            this.remove(jmMdwxEntityQueryWrapper);
            jmMdwxTfService.remove(jmMdwxTfEntityQueryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getJmMdwxPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmMdwx> jmMdwxes = new ArrayList<>();
        IPage<JmMdwx> jmMdwxDTO = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        IPage<JmMdwxEntity> jmMdwxEntityIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        if (jmMdwxDTO==null){
            result.setAll(10001,null,"参数错误");
        }else{
            List<JmMdwxEntity> mdwx = jmMdwxEntityIPage.getRecords();
            for (JmMdwxEntity md : mdwx){
                JmMdwx jmMdwx = new JmMdwx();
                QueryWrapper<JmMdwxTfEntity> jmMdwxTfEntityQueryWrapper = new QueryWrapper<>();
                jmMdwxTfEntityQueryWrapper.eq("sid",md.getSid());
                List<JmMdwxTfDTO> jmMdwxTfDTOS = jmMdwxTfService.select(jmMdwxTfEntityQueryWrapper);
                jmMdwx.setJmMdwxDTO(ConvertUtils.convert(md,currentDtoClass()));
                jmMdwx.setJmMdwxTfDTOs(jmMdwxTfDTOS);
                //查询模具名称
                QueryWrapper<JmMouldEntity> jmMouldEntityQueryWrapper = new QueryWrapper<>();
                jmMouldEntityQueryWrapper.eq("md_no",md.getMdNo());
                JmMouldDTO jmMouldDTO = jmMouldService.selectOne(jmMouldEntityQueryWrapper);
                jmMdwx.setJmMouldDTO(jmMouldDTO);
                jmMdwxes.add(jmMdwx);
            }
            jmMdwxDTO.setRecords(jmMdwxes);
            result.setAll(20000,jmMdwxDTO,"查找成功");
        }
        return result;
    }

    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();

        if(dto.getAscOrder()==null && dto.getDescOrder()==null){
            dto.setDescOrder("hpdate");
        }

        if (MyUtils.StringIsNull(dto.getSid())){
            queryWrapper.eq("sid",dto.getSid());
        }
        if (MyUtils.StringIsNull(dto.getType())){
            queryWrapper.eq("sbuid",dto.getType());
        }
        if (MyUtils.StringIsNull(dto.getSorg())){
            queryWrapper.eq("dep",dto.getSorg());
        }
        if (MyUtils.StringIsNull(dto.getWkNo())){
            queryWrapper.eq("wk_no",dto.getWkNo());
        }
        if (MyUtils.StringIsNull(dto.getMouldNo())){
            queryWrapper.eq("md_no",dto.getMouldNo());
        }
        if (MyUtils.StringIsNull(dto.getType())){
            queryWrapper.eq("ed_locked",dto.getType());
        }
        if (dto.getState()!=null){
            queryWrapper.eq("state",dto.getState());
        }
        if (dto.getState()==null && dto.getOtherState()!=null){
            queryWrapper.in("state",dto.getOtherState());
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
