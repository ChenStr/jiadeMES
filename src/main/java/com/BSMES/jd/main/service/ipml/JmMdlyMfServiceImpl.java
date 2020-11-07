package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmMdlyMfDao;
import com.BSMES.jd.main.dao.JmMouldDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.InsorgEntity;
import com.BSMES.jd.main.entity.JmBomTfEntity;
import com.BSMES.jd.main.entity.JmMdlyMfEntity;
import com.BSMES.jd.main.entity.JmMdlyTfEntity;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.main.service.JmMdlyMfService;
import com.BSMES.jd.main.service.JmMdlyTfService;
import com.BSMES.jd.main.service.JmMouldService;
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
public class JmMdlyMfServiceImpl extends BaseServiceImpl<JmMdlyMfDao, JmMdlyMfEntity, JmMdlyMfDTO> implements JmMdlyMfService {

    @Autowired
    InssysvarService inssysvarService;


    @Autowired
    JmMdlyTfService jmMdlyTfService;

    @Autowired
    JmMouldService jmMouldService;


    @Override
    public void beforeInsert(JmMdlyMfDTO dto) {
        dto.setHpdate(new Date());
    }

    @Override
    public void beforEedit(JmMdlyMfDTO dto) {

    }

    @Override
    public CommonReturn getMdlyMf(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmMdly> jmMdlies = new ArrayList<>();
        List<JmMdlyMfDTO> dtos = this.select(this.getQueryWrapper(dto));
        if(dtos.isEmpty()){
            result.setAll(20000,jmMdlies,"没有查找结果，建议仔细核对查找条件");
        }else{
            for (JmMdlyMfDTO dto1 : dtos){
                JmMdly jmMdly = new JmMdly();
                QueryWrapper<JmMdlyTfEntity> jmMdlyTfEntityQueryWrapper = new QueryWrapper<>();
                jmMdlyTfEntityQueryWrapper.eq("sid",dto1.getSid());
                List<JmMdlyTfDTO> jmMdlyTfDTOS = jmMdlyTfService.select(jmMdlyTfEntityQueryWrapper);
                jmMdly.setJmMdlyMfDTO(dto1);
                jmMdly.setJmMdlyTfDTOS(jmMdlyTfDTOS);
                jmMdlies.add(jmMdly);
            }
            result.setAll(20000,jmMdlies,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveMdlyMf(JmMdly dto) {
        CommonReturn result = new CommonReturn();
        Boolean flag = true;
        String sid = null;

        if (dto.getJmMdlyMfDTO()!=null && dto.getJmMdlyMfDTO().getSid()!=null){
            flag=false;
            sid = dto.getJmMdlyMfDTO().getSid();
            this.edit(dto.getJmMdlyMfDTO());
        }else{
            sid = this.getKey("Mdly","sid",inssysvarService,dto.getJmMdlyMfDTO());
            dto.getJmMdlyMfDTO().setSid(sid);
            this.insert(dto.getJmMdlyMfDTO());
        }

        try{
            //如果是编辑的话
            if (flag==false){
                //删除所有原始数据
                QueryWrapper<JmMdlyTfEntity> jmMdlyMfEntityQueryWrapper = new QueryWrapper<>();
                jmMdlyMfEntityQueryWrapper.eq("sid",dto.getJmMdlyMfDTO().getSid());
                jmMdlyTfService.remove(jmMdlyMfEntityQueryWrapper);
            }
            //将新的数据添加进去
            for (JmMdlyTfDTO jmMdlyTfDTO : dto.getJmMdlyTfDTOS()){
                jmMdlyTfDTO.setSid(sid);
                if (jmMdlyTfDTO!=null && jmMdlyTfDTO.getSid()!=null){
                    jmMdlyTfService.saveMdlyTfs(dto.getJmMdlyTfDTOS());
                }
                JmMouldDTO jmMouldDTO = new JmMouldDTO();
                jmMouldDTO.setMdNo(jmMdlyTfDTO.getMdNo());
                jmMouldDTO.setTypeid(1);
                jmMouldDTO.setState(dto.getJmMdlyMfDTO().getState().toString());
                jmMouldService.editMould(jmMouldDTO);
            }
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public CommonReturn editMdlyMf(JmMdlyMfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            //获取原先的用户属性值
            QueryWrapper<JmMdlyMfEntity> jmMdlyMfEntityQueryWrapper = new QueryWrapper<>();
            jmMdlyMfEntityQueryWrapper.eq("sid",dto.getSid());
            JmMdlyMfDTO var = this.selectOne(jmMdlyMfEntityQueryWrapper);
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
    public CommonReturn delMdlyMf(List<String> sids) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmMdlyMfEntity> queryWrapper = new QueryWrapper<>();
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
    public CommonReturn getMdlyMfPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<JmMdly> jmMdlyIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        IPage<JmMdlyMfEntity> jmMdlyMfEntityIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        if (jmMdlyIPage==null){
            result.setAll(10001,null,"参数错误");
        }else{
            List<JmMdly> jmMdlies = new ArrayList<>();
            List<JmMdlyMfEntity> jmMdlyMfEntities = jmMdlyMfEntityIPage.getRecords();
            //将子表数据填入
            for (JmMdlyMfEntity dto1 : jmMdlyMfEntities){
                JmMdly jmMdly = new JmMdly();
                QueryWrapper<JmMdlyTfEntity> jmMdlyTfEntityQueryWrapper = new QueryWrapper<>();
                jmMdlyTfEntityQueryWrapper.eq("sid",dto1.getSid());
                List<JmMdlyTfDTO> jmMdlyTfDTOS = jmMdlyTfService.select(jmMdlyTfEntityQueryWrapper);
                jmMdly.setJmMdlyMfDTO(ConvertUtils.convert(dto1,currentDtoClass()));
                jmMdly.setJmMdlyTfDTOS(jmMdlyTfDTOS);
            }
            jmMdlyIPage.setRecords(jmMdlies);
            result.setAll(20000,jmMdlyIPage,"查找成功");
        }
        return result;
    }

    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();

        //筛选
        if (MyUtils.StringIsNull(dto.getSid())){
            queryWrapper.like("sid",dto.getSid());
        }
        if (MyUtils.StringIsNull(dto.getSorg())){
            queryWrapper.like("sorg",dto.getSorg());
        }
        if (MyUtils.StringIsNull(dto.getDep())){
            queryWrapper.like("dep",dto.getDep());
        }
        if (dto.getState()!=null){
            queryWrapper.like("state",dto.getState());
        }

        if (dto.getBegDd()!=null){
            queryWrapper.ge("hpdate",dto.getBegDd());
        }
        if(dto.getEndDd()!=null){
            queryWrapper.le("hpdate",dto.getEndDd());
        }

        //排序
        if (dto.getAscOrder()!=null){
            queryWrapper.orderByAsc(MyUtils.humpToLine((String) dto.getAscOrder()));
        }
        if (dto.getDescOrder()!=null && dto.getAscOrder()==null){
            queryWrapper.orderByDesc(MyUtils.humpToLine((String) dto.getDescOrder()));
        }


        return queryWrapper;
    }
}
