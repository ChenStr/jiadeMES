package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmGzstdMfDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.*;
import com.BSMES.jd.main.service.*;
import com.BSMES.jd.tools.ConvertUtils;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class JmGzstdMfServiceImpl extends BaseServiceImpl<JmGzstdMfDao, JmGzstdMfEntity, JmGzstdMfDTO> implements JmGzstdMfService {

    @Autowired
    JmGzstdTfService jmGzstdTfService;

    @Autowired
    JmWxIdService jmWxIdService;

    @Autowired
    InssysvarService inssysvarService;

    @Autowired
    JmMouldService jmMouldService;

    @Override
    public void beforeInsert(JmGzstdMfDTO dto) {

    }

    @Override
    public void beforEedit(JmGzstdMfDTO dto) {

    }

    @DS("master")
    @Override
    public CommonReturn getGzstd(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmGzstd> dtos = new ArrayList<>();
        List<JmGzstdMfDTO> jmGzstdMfDTOS = this.select(getQueryWrapper(dto));
        if(jmGzstdMfDTOS.isEmpty()){
            result.setAll(20000,jmGzstdMfDTOS,"没有查找结果，建议仔细核对查找条件");
        }else{
            for(JmGzstdMfDTO jmGzstdMfDTO:jmGzstdMfDTOS){
                JmGzstd jmGzstd = new JmGzstd();
                List<String> mtIds = new ArrayList<>();
                QueryWrapper<JmMouldEntity> jmMouldEntityQueryWrapper = new QueryWrapper<>();
                jmMouldEntityQueryWrapper.eq("md_no",jmGzstdMfDTO.getMdNo());
                JmMouldDTO jmMouldDTO = jmMouldService.selectOne(jmMouldEntityQueryWrapper);
                QueryWrapper<JmGzstdTfEntity> jmGzstdTfEntityQueryWrapper = new QueryWrapper<>();
                jmGzstdTfEntityQueryWrapper.eq("gzstd_no",jmGzstdMfDTO.getGzstdNo());

                List<JmGzstdTfDTO> jmGzstdTfDTOS = jmGzstdTfService.select(jmGzstdTfEntityQueryWrapper);
                if (jmGzstdTfDTOS!=null && jmGzstdTfDTOS.size()>0){
                    for (JmGzstdTfDTO jmGzstdTfDTO : jmGzstdTfDTOS){
                        if (jmGzstdTfDTO.getWxId()!=null && jmGzstdTfDTO.getWxId().length()>0){
                            mtIds.add(jmGzstdTfDTO.getWxId());
                        }
                    }
                }
                List<JmWxIdDTO> jmWxIdDTOS = new ArrayList<>();
                if (mtIds!=null && mtIds.size()>0){
                    QueryWrapper<JmWxIdEntity> jmWxIdEntityQueryWrapper = new QueryWrapper<>();
                    jmWxIdEntityQueryWrapper.in("mt_id",mtIds);
                    jmWxIdDTOS = jmWxIdService.select(jmWxIdEntityQueryWrapper);
                }
                List<JmGzstdTf> jmGzstdTfs = new ArrayList<>();

                for (JmGzstdTfDTO jmGzstdTfDTO : jmGzstdTfDTOS){
                    JmGzstdTf jmGzstdTf = new JmGzstdTf();
                    jmGzstdTf.setJmGzstdTfDTO(jmGzstdTfDTO);

                    for (JmWxIdDTO jmWxIdDTO : jmWxIdDTOS){
                        if (jmWxIdDTO.getMtId().equals(jmGzstdTfDTO.getWxId())){
                            jmGzstdTf.setJmWxIdDTO(jmWxIdDTO);
                        }
                    }
                    jmGzstdTfs.add(jmGzstdTf);
                }

                jmGzstd.setJmMouldDTO(jmMouldDTO);
                jmGzstd.setJmGzstdTfs(jmGzstdTfs);
                jmGzstd.setJmGzstdMfDTO(jmGzstdMfDTO);
                dtos.add(jmGzstd);
            }
            result.setAll(20000,dtos,"查找成功");
        }
        return result;
    }

    @DS("master")
//    @Transactional
    @Override
    public CommonReturn saveGzstd(JmGzstd dto) {
        CommonReturn result = new CommonReturn();
        Boolean flag = true;
        String sid = null;

        //首先判断是添加还是修改
        if (dto.getJmGzstdMfDTO()!=null && dto.getJmGzstdMfDTO().getGzstdNo()!=null){
            flag=false;
            sid = dto.getJmGzstdMfDTO().getGzstdNo();
            this.edit(dto.getJmGzstdMfDTO());
        }else{
            sid = this.getKey("Gzstd","gzstd_no",inssysvarService,dto.getJmGzstdMfDTO());
            dto.getJmGzstdMfDTO().setGzstdNo(sid);
            this.insert(dto.getJmGzstdMfDTO());
        }

        try{

            //如果是编辑的话先进行删除
            if (flag==false){
                //删除所有的原始数据
                QueryWrapper<JmGzstdTfEntity> jmMdbfTfEntityQueryWrapper = new QueryWrapper<>();
                jmMdbfTfEntityQueryWrapper.eq("gzstd_no",sid);
                jmGzstdTfService.remove(jmMdbfTfEntityQueryWrapper);
            }
            //将新的数据新增进去
            if(dto.getJmGzstdTfs()!=null && dto.getJmGzstdTfs().size()>0){
                List<JmGzstdTfDTO> jmGzstdTfDTOS = new ArrayList<>();
                for(JmGzstdTf jmGzstdTfDTO : dto.getJmGzstdTfs()){
                    jmGzstdTfDTO.getJmGzstdTfDTO().setGzstdNo(sid);
                    jmGzstdTfDTOS.add(jmGzstdTfDTO.getJmGzstdTfDTO());
                }
                jmGzstdTfService.saveGzstds(jmGzstdTfDTOS);
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
    public CommonReturn editGzstd(JmGzstdMfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getGzstdNo())){
            //获取原先的用户属性值
            QueryWrapper<JmGzstdMfEntity> jmGzstdMfEntityQueryWrapper = new QueryWrapper<>();
            jmGzstdMfEntityQueryWrapper.eq("gzstd_no",dto.getGzstdNo());
            JmGzstdMfDTO var = this.selectOne(jmGzstdMfEntityQueryWrapper);
            //设置用户不能操作的属性
            try{
                if (dto.getScbyDd()==null){
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + dto.getByzqDd());
                    dto.setScbyDd(calendar.getTime());
                }
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
    public CommonReturn delGzstd(List<String> gzstdNos) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmGzstdMfEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("gzstd_no",gzstdNos);
        QueryWrapper<JmGzstdTfEntity> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.in("gzstd_no",gzstdNos);
        try{
            jmGzstdTfService.remove(queryWrapper1);
            this.remove(queryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn getGzstdPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<JmGzstdMfEntity> jmGzstdMfEntityIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        IPage<JmGzstd> jmGzstdIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        List<JmGzstd> jmGzstds = new ArrayList<>();
        if (jmGzstdMfEntityIPage==null){
            result.setAll(10001,null,"参数错误");
        }else{
            for(JmGzstdMfEntity jmGzstdMf : jmGzstdMfEntityIPage.getRecords()){
                JmGzstd jmGzstd = new JmGzstd();
                List<String> mtIds = new ArrayList<>();
                QueryWrapper<JmMouldEntity> jmMouldEntityQueryWrapper = new QueryWrapper<>();
                jmMouldEntityQueryWrapper.eq("md_no",jmGzstdMf.getMdNo());
                JmMouldDTO jmMouldDTO = jmMouldService.selectOne(jmMouldEntityQueryWrapper);
                QueryWrapper<JmGzstdTfEntity> jmGzstdTfEntityQueryWrapper = new QueryWrapper<>();
                jmGzstdTfEntityQueryWrapper.eq("gzstd_no",jmGzstdMf.getGzstdNo());

                List<JmGzstdTfDTO> jmGzstdTfDTOS = jmGzstdTfService.select(jmGzstdTfEntityQueryWrapper);
                if (jmGzstdTfDTOS!=null && jmGzstdTfDTOS.size()>0){
                    for (JmGzstdTfDTO jmGzstdTfDTO : jmGzstdTfDTOS){
                        if (jmGzstdTfDTO.getWxId()!=null && jmGzstdTfDTO.getWxId().length()>0){
                            mtIds.add(jmGzstdTfDTO.getWxId());
                        }
                    }
                }
                List<JmWxIdDTO> jmWxIdDTOS = new ArrayList<>();
                if (mtIds!=null && mtIds.size()>0){
                    QueryWrapper<JmWxIdEntity> jmWxIdEntityQueryWrapper = new QueryWrapper<>();
                    jmWxIdEntityQueryWrapper.in("mt_id",mtIds);
                    jmWxIdDTOS = jmWxIdService.select(jmWxIdEntityQueryWrapper);
                }
                List<JmGzstdTf> jmGzstdTfs = new ArrayList<>();

                for (JmGzstdTfDTO jmGzstdTfDTO : jmGzstdTfDTOS){
                    JmGzstdTf jmGzstdTf = new JmGzstdTf();
                    jmGzstdTf.setJmGzstdTfDTO(jmGzstdTfDTO);

                    for (JmWxIdDTO jmWxIdDTO : jmWxIdDTOS){
                        if (jmWxIdDTO.getMtId().equals(jmGzstdTfDTO.getWxId())){
                            jmGzstdTf.setJmWxIdDTO(jmWxIdDTO);
                        }
                    }
                    jmGzstdTfs.add(jmGzstdTf);
                }




                jmGzstd.setJmMouldDTO(jmMouldDTO);
                jmGzstd.setJmGzstdTfs(jmGzstdTfs);
                jmGzstd.setJmGzstdMfDTO(ConvertUtils.convert(jmGzstdMf,currentDtoClass()));
                jmGzstds.add(jmGzstd);
            }
            jmGzstdIPage.setRecords(jmGzstds);
            result.setAll(20000,jmGzstdIPage,"查找成功");
        }
        return result;
    }

    /**
     * 填写筛选数据
     * @param dto
     * @return
     */
    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();

        if (MyUtils.StringIsNull(dto.getSid())){
            queryWrapper.eq("gzstd_no",dto.getSid());
        }
        if (MyUtils.StringIsNull(dto.getOtherId())){
            queryWrapper.like("name",dto.getOtherId());
        }
        if (MyUtils.StringIsNull(dto.getMouldNo())){
            queryWrapper.eq("md_no",dto.getMouldNo());
        }
        if (MyUtils.StringIsNull(dto.getType())){
            queryWrapper.eq("md_grp",dto.getType());
        }
        if (dto.getState()!=null){
            queryWrapper.like("state",dto.getState());
        }
        if (MyUtils.StringIsNull(dto.getSorg())){
            queryWrapper.eq("dep",dto.getSorg());
        }
        if (MyUtils.StringIsNull(dto.getDep())){
            queryWrapper.like("dep_name",dto.getDep());
        }
        if (dto.getBegDd()!=null){
            queryWrapper.ge("scby_dd",dto.getBegDd());
        }
        if(dto.getEndDd()!=null){
            queryWrapper.le("scby_dd",dto.getEndDd());
        }

        if (dto.getBegDd()!=null){
            queryWrapper.ge("scby_dd",dto.getBegDd());
        }
        if(dto.getEndDd()!=null){
            queryWrapper.le("scby_dd",dto.getEndDd());
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
