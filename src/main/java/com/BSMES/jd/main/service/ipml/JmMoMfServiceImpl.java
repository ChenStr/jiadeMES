package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.BaseDTO;
import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.BaseService;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmMoMfDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.*;
import com.BSMES.jd.main.service.*;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JmMoMfServiceImpl extends BaseServiceImpl<JmMoMfDao , JmMoMfEntity , JmMoMfDTO > implements JmMoMfService {

    @Autowired
    InsorgService insorgService;

    @Autowired
    JmPrdtService jmPrdtService;

    @Autowired
    JmPrdtutService jmPrdtutService;

    @Autowired
    InssysvarService inssysvarService;

    @Autowired
    JmJobService jmJobService;

    @Autowired
    JmBomMfService jmBomMfService;

    @Autowired
    JmBomTfService jmBomTfService;

    @Autowired
    JmMoMfDao jmMoMfDao;

    @Override
    public void beforeInsert(JmMoMfDTO dto) {
        dto.setAstRelease(1);
        dto.setHpdate(new Date());
        dto.setModitime(new Date());
        dto.setState(6);
        dto.setSbuid("MO");
    }

    @Override
    public void beforEedit(JmMoMfDTO dto) {

    }

    @Override
    public CommonReturn getMoMf(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmMoMfMore> moMfs = jmMoMfDao.getMoMfMore(dto);
        if(moMfs.isEmpty()){

            //查询其中的原料信息
            for (JmMoMfMore more : moMfs){
                QueryWrapper<JmBomMfEntity> jmBomMfEntityQueryWrapper = new QueryWrapper<>();
                jmBomMfEntityQueryWrapper.eq("prd_no",more.getJmMoMfDTO().getPrdNo());
                JmBomMfDTO jmBomMf = jmBomMfService.selectOne(jmBomMfEntityQueryWrapper);
                if (jmBomMf==null && jmBomMf.getBomNo()==null){
                    break;
                }

                QueryWrapper<JmBomTfEntity> jmBomTfEntityQueryWrapper = new QueryWrapper<>();
                jmBomTfEntityQueryWrapper.eq("bom_no",jmBomMf.getBomNo());
                List<JmBomTfDTO> jmBomTfDTOS = jmBomTfService.select(jmBomTfEntityQueryWrapper);

                if (jmBomTfDTOS==null && jmBomTfDTOS.size()<=0){
                    break;
                }

                List<String> prdNos = new ArrayList<>();
                jmBomTfDTOS.stream().forEach(T->prdNos.add(T.getPrdNo()));
                QueryWrapper<JmPrdtEntity> jmPrdtEntityQueryWrapper = new QueryWrapper<>();
                jmPrdtEntityQueryWrapper.in("prd_no",prdNos);
                List<JmPrdtDTO> jmPrdtDTOS = jmPrdtService.select(jmPrdtEntityQueryWrapper);

                more.setPrdts(jmPrdtDTOS);
            }

            result.setAll(20000,moMfs,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,moMfs,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveMoMf(JmMoMfDTO dto) {
        CommonReturn result = new CommonReturn();
        dto.setSid(this.getKey("JmMoMf","sid",inssysvarService,dto));
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid()) && MyUtils.StringIsNull(dto.getSorg()) && MyUtils.StringIsNull(dto.getPrdNo()) && dto.getQty()!=null && dto.getEndDd()!=null){
            QueryWrapper<JmMoMfEntity> moMfQueryWrapper = new QueryWrapper<>();
            //判断是否有相同的制令单号了
            moMfQueryWrapper.eq("sid",dto.getSid());
            JmMoMfDTO moMf = this.selectOne(moMfQueryWrapper);
            //判断 sid 是否重复
            if (moMf==null || moMf.getSid()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"制令单号已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn editMoMf(JmMoMfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            //获取原先的人员属性值
            QueryWrapper<JmMoMfEntity> moMfQueryWrapper = new QueryWrapper<>();
            moMfQueryWrapper.eq("sid",dto.getSid());
            JmMoMfDTO moMf = this.selectOne(moMfQueryWrapper);
            //查看该调度单下是否有计划分派
            QueryWrapper jobQueryWrapper = new QueryWrapper();
            jobQueryWrapper.eq("sid",dto.getSid());
            List<JmJobDTO> jmJobs = jmJobService.select(jobQueryWrapper);
            if (jmJobs==null || jmJobs.size()==0){
                //设置用户不能操作的属性
                try{
                    this.edit(dto);
                    result.setAll(20000,null,"操作成功");
                }catch (Exception e){
                    result.setAll(10001,null,"操作失败");
                }
            }else{
                //强制终止调度单
                moMf.setState(12);
                //将其生产计划单的状态也改为12
                QueryWrapper<JmJobEntity> jmJobEntityQueryWrapper = new QueryWrapper<>();
                jmJobEntityQueryWrapper.eq("sid",dto.getSid());
                List<JmJobDTO> jmJobDTOS = jmJobService.select(jmJobEntityQueryWrapper);
                for (JmJobDTO jmJobDTO : jmJobDTOS){
                    jmJobDTO.setState("12");
                    jmJobService.editJob(jmJobDTO);
                }
                this.edit(moMf);
                result.setAll(20000,null,"终止生产成功");
            }

        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn delMoMf(List<String> sids) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmMoMfEntity> moMfQueryWrapper = new QueryWrapper<>();
        moMfQueryWrapper.in("sid",sids);
        //查看该调度单下面是否有作业计划单
        QueryWrapper jobQueryWrapper = new QueryWrapper();
        jobQueryWrapper.in("sid",sids);
        List<JmJobDTO> jmJobs = jmJobService.select(jobQueryWrapper);
        if (jmJobs==null || jmJobs.size()==0){
            try{
                this.remove(moMfQueryWrapper);
                result.setAll(20000,null,"操作成功");
            }catch (Exception e) {
                result.setAll(10001, null, "操作失败");
            }
        }else{
            result.setAll(40000,null,"该调度单下已经有计划分派了，不能删除");
        }

        return result;
    }

    @Override
    public CommonReturn getMoMfPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        if (dto.getDescOrder()==null && dto.getAscOrder()==null){
            dto.setDescOrder("create_date");
        }
        if (dto.getPage()==null){
            dto.setPage(1);
        }
        if (dto.getPageSize()==null){
            dto.setPageSize(10);
        }
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        List<JmMoMfMore> jmMoMfMores = (List<JmMoMfMore>) this.getMoMf(dto).getData();

        for (JmMoMfMore more : jmMoMfMores){
            QueryWrapper<JmBomMfEntity> jmBomMfEntityQueryWrapper = new QueryWrapper<>();
            jmBomMfEntityQueryWrapper.eq("prd_no",more.getJmMoMfDTO().getPrdNo());
            JmBomMfDTO jmBomMf = jmBomMfService.selectOne(jmBomMfEntityQueryWrapper);
            if (jmBomMf==null && jmBomMf.getBomNo()==null){
                break;
            }

            QueryWrapper<JmBomTfEntity> jmBomTfEntityQueryWrapper = new QueryWrapper<>();
            jmBomTfEntityQueryWrapper.eq("bom_no",jmBomMf.getBomNo());
            List<JmBomTfDTO> jmBomTfDTOS = jmBomTfService.select(jmBomTfEntityQueryWrapper);

            if (jmBomTfDTOS==null && jmBomTfDTOS.size()<=0){
                break;
            }

            List<String> prdNos = new ArrayList<>();
            jmBomTfDTOS.stream().forEach(T->prdNos.add(T.getPrdNo()));
            if (prdNos!=null && prdNos.size()>0){
                QueryWrapper<JmPrdtEntity> jmPrdtEntityQueryWrapper = new QueryWrapper<>();
                jmPrdtEntityQueryWrapper.in("prd_no",prdNos);
                List<JmPrdtDTO> jmPrdtDTOS = jmPrdtService.select(jmPrdtEntityQueryWrapper);
                more.setPrdts(jmPrdtDTOS);
            }
        }

        PageInfo jmMoMfMorePageInfo = new PageInfo<JmMoMfMore>(jmMoMfMores);

        result.setAll(20000,jmMoMfMorePageInfo,"操作成功");
        return result;
    }

    /**
     * 筛选条件
     * @param dto
     * @return
     */
    private QueryWrapper getQueryWrapper(JmMoMfDTO dto){
        QueryWrapper queryWrapper = new QueryWrapper();
        if (MyUtils.StringIsNull(dto.getSid())){
            queryWrapper.eq("sid",dto.getSid());
        }
        if (MyUtils.StringIsNull(dto.getSorg())){
            queryWrapper.eq("sorg",dto.getSorg());
        }
        if (MyUtils.StringIsNull(dto.getPrdNo())){
            queryWrapper.eq("prd_no",dto.getPrdNo());
        }
        if (dto.getState()!=null){
            queryWrapper.eq("state",dto.getState());
        }
        if (MyUtils.StringIsNull(dto.getPrdName())){
            queryWrapper.like("prd_name",dto.getPrdName());
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
        if (dto.getDescOrder()!=null){
            queryWrapper.orderByDesc(MyUtils.humpToLine((String) dto.getDescOrder()));
        }
        return queryWrapper;
    }


}
