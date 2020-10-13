package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmJobDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.*;
import com.BSMES.jd.main.service.*;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JmJobServiceImpl extends BaseServiceImpl<JmJobDao , JmJobEntity , JmJobDTO> implements JmJobService {

    @Autowired
    JmMoMfService jmMoMfService;

    @Autowired
    JmJobDao jmJobDao;

    @Autowired
    InssysvarService inssysvarService;

    @Autowired
    JmDevService jmDevService;

    @Autowired
    JmMouldService jmMouldService;

    @Autowired
    InsorgService insorgService;

    @Autowired
    JmJobRecService jmJobRecService;

    @Autowired
    JmPrdtService jmPrdtService;

    @Autowired
    JmBomMfService jmBomMfService;

    @Autowired
    JmBomTfService jmBomTfService;

    @Autowired
    JmJobRecBService jmJobRecBService;


    @Override
    public void beforeInsert(JmJobDTO dto) {

    }

    @Override
    public void beforEedit(JmJobDTO dto) {

    }

    @Override
    public CommonReturn getJob(JmJobDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto,true);
        List<JmJobDTO> jobs = this.select(data);
        if(jobs.isEmpty()){
            result.setAll(20000,jobs,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,jobs,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn joinFindJobs(JobJoin jobJoin) {
        CommonReturn result = new CommonReturn();
        //驼峰转换
        if (jobJoin.getAscOrder()!=null){
            jobJoin.setAscOrder(MyUtils.humpToLine((String) jobJoin.getAscOrder()));
        }else if(jobJoin.getDescOrder()!=null){
            jobJoin.setDescOrder(MyUtils.humpToLine((String) jobJoin.getDescOrder()));
        }
        List<JobJoin> jobJoins = jmJobDao.joinFindJob(jobJoin);
        for (JobJoin jobJoin1 : jobJoins){
            //查询产品规格
            QueryWrapper<JmPrdtEntity> jmPrdtQueryWrapper = new QueryWrapper<>();
            jmPrdtQueryWrapper.eq("prd_no",jobJoin1.getPrdNo());
            JmPrdtDTO prdtDTO = jmPrdtService.selectOne(jmPrdtQueryWrapper);
            if (prdtDTO!=null && prdtDTO.getPrdNo()!=null){
                jobJoin1.setSpc(prdtDTO.getSpc());
            }
            //原料信息
            QueryWrapper<JmBomMfEntity> jmBomMfEntityQueryWrapper = new QueryWrapper<>();
            jmBomMfEntityQueryWrapper.eq("prd_no",jobJoin1.getPrdNo());
            JmBomMfDTO jmBomMfDTO = jmBomMfService.selectOne(jmBomMfEntityQueryWrapper);
            QueryWrapper<JmBomTfEntity> jmBomTfEntityQueryWrapper = new QueryWrapper<>();
            jmBomTfEntityQueryWrapper.eq("bom_no",jmBomMfDTO.getBomNo());
            List<JmBomTfDTO> jmBomTfDTO = jmBomTfService.select(jmBomTfEntityQueryWrapper);
            List<String> prdNos = new ArrayList<>();
            jmBomTfDTO.stream().forEach(T->prdNos.add(T.getPrdNo()));
            QueryWrapper<JmPrdtEntity> jmPrdtEntityQueryWrapper = new QueryWrapper<>();
            jmPrdtEntityQueryWrapper.in("prd_no",prdNos);
            List<JmPrdtDTO> prdtDTOS = jmPrdtService.select(jmPrdtEntityQueryWrapper);
            jobJoin1.setPrdts(prdtDTOS);
            //已生产数量给算出来
            BigDecimal sum = new BigDecimal("0");
            QueryWrapper<JmJobRecEntity> jmJobRecEntityQueryWrapper = new QueryWrapper<>();
            jmJobRecEntityQueryWrapper.eq("jb_no",jobJoin1.getJbNo());
            List<JmJobRecDTO> jmJobRecDTOS = jmJobRecService.select(jmJobRecEntityQueryWrapper);

            if (jmJobRecDTOS!=null && jmJobRecDTOS.size()>0){
                for (JmJobRecDTO jmJobRecDTO : jmJobRecDTOS){
//                    sum = sum.add(jmJobRecDTO.getQtyCur());
                    QueryWrapper<JmJobRecBEntity> jmJobRecBEntityQueryWrapper = new QueryWrapper<>();
                    jmJobRecBEntityQueryWrapper.eq("opsid",jmJobRecDTO.getOpsid());
                    List<JmJobRecBDTO> jmJobRecBDTOS = jmJobRecBService.select(jmJobRecBEntityQueryWrapper);
                    if (jmJobRecBDTOS!=null && jmJobRecBDTOS.size()>0){
                        for (JmJobRecBDTO jmJobRecBDTO : jmJobRecBDTOS){
                            sum = sum.add(jmJobRecBDTO.getQtyOk());
                        }
                    }
                }
            }
            jobJoin1.setQtyAlready(sum);
        }
        result.setAll(20000,jobJoins,"操作成功");
        return result;
    }

    @Override
    public CommonReturn saveJob(JmJobDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid()) && dto.getCid()!=null ){
            QueryWrapper<JmJobEntity> jobQueryWrapper = new QueryWrapper<>();
            //判断制令单号是否存在 , 不存在不能添加
            QueryWrapper<JmMoMfEntity> moMfQueryWrapper = new QueryWrapper<>();
            JmMoMfDTO jmMoMf = jmMoMfService.selectOne(moMfQueryWrapper.eq("sid",dto.getSid()));
            if (jmMoMf==null || jmMoMf.getSid() == null){
                result.setAll(10001,null,"制令单号不存在不能新增，不能新增!");
            }else{
                jobQueryWrapper.eq("sid",dto.getSid());
                jobQueryWrapper.eq("cid",dto.getCid());
                JmJobDTO job = this.selectOne(jobQueryWrapper);
                //判断 usrcode 是否重复
                if (job==null || job.getSid()==null){
                    this.insert(dto);
                    result.setAll(20000,null,"操作成功");
                }else{
                    result.setAll(10001,null,"生产计划单已经存在，不能新增!");
                }
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Transactional
    @Override
    public CommonReturn saveJobs(JobSave jobSave) {
        CommonReturn result = new CommonReturn();

        List<JmJobDTO> dtos = jobSave.getJmJobDTOS();
        BigDecimal sum = new BigDecimal("0");
        //将数据格式补充完毕
        if (dtos!=null && dtos.size()>0){
            for (int i=0 ; i < dtos.size() ; i++){
                //判定调度单号(制令单)是否存在 sid
                String key = this.getKey("JmJob","jb_no",inssysvarService,dtos.get(i));
                //首先先找到编码规则
                QueryWrapper queryWrapper = new QueryWrapper();
                queryWrapper.eq("sname","JmJob");
                String code = inssysvarService.selectOne(queryWrapper).getSbds();
                //获取括号前的数据
                String after = code.substring(code.indexOf('%')+1,code.length());
                String before = key.substring(0,key.length() - after.length());
                String keyafter = key.substring(key.length()-after.length(),key.length());
                key = before + MyUtils.geFourNumber(Integer.parseInt(keyafter)+i,after.length());
                dtos.get(i).setJbNo(key);
                dtos.get(i).setCreateDate(new Date());
                //查出设备信息
                QueryWrapper<JmDevEntity> jmDevDTOQueryWrapper = new QueryWrapper<>();
                jmDevDTOQueryWrapper.eq("dev_no",dtos.get(i).getRsNo());
                JmDevDTO dev = jmDevService.selectOne(jmDevDTOQueryWrapper);
                dtos.get(i).setDevName(dev.getName());
                //查询模具信息
                QueryWrapper<JmMouldEntity> jmMouldEntityQueryWrapper = new QueryWrapper<>();
                jmMouldEntityQueryWrapper.eq("md_no",dtos.get(i).getMdNo());
                JmMouldDTO mould = jmMouldService.selectOne(jmMouldEntityQueryWrapper);
                dtos.get(i).setMdName(mould.getName());
                //部门信息
                QueryWrapper<InsorgEntity> insorgEntityQueryWrapper = new QueryWrapper<>();
                insorgEntityQueryWrapper.eq("orgcode",dtos.get(i).getSorg());
                InsorgDTO sorg = insorgService.selectOne(insorgEntityQueryWrapper);
                dtos.get(i).setDep(sorg.getOrgname());
                //查出产品信息
                QueryWrapper<JmPrdtEntity> prdtEntityQueryWrapper = new QueryWrapper<>();
                prdtEntityQueryWrapper.eq("prd_no",dtos.get(i).getPrdNo());
                JmPrdtDTO jmPrdtDTO = jmPrdtService.selectOne(prdtEntityQueryWrapper);
                dtos.get(i).setPrdName(jmPrdtDTO.getName());
                sum = sum.add(dtos.get(i).getQty());
                //将时间放入
                dtos.get(i).setCreateDate(new Date());
                dtos.get(i).setState("546");
            }
        }
        try{
            //先进行删除后进行添加
            QueryWrapper<JmJobEntity> jmJobEntityQueryWrapper = new QueryWrapper<>();
            jmJobEntityQueryWrapper.eq("sid",jobSave.getJmMoMfDTO().getSid());
            this.remove(jmJobEntityQueryWrapper);
            //相加
            QueryWrapper<JmMoMfEntity> jmMoMfEntityQueryWrapper = new QueryWrapper<>();
            jmMoMfEntityQueryWrapper.eq("sid",jobSave.getJmMoMfDTO().getSid());
            JmMoMfDTO jmMoMfDTO = jmMoMfService.selectOne(jmMoMfEntityQueryWrapper);
            if (jmMoMfDTO!=null && jmMoMfDTO.getSid()!=null){
                jmMoMfDTO.setQtyAlled(sum);
                jmMoMfService.editMoMf(jmMoMfDTO);
            }
            jmJobDao.insertJmJobs(dtos);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(10001,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CommonReturn editJob(JmJobDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid()) && dto.getCid()!=null ){
            //获取原先的人员属性值
            QueryWrapper<JmJobEntity> jobQueryWrapper = new QueryWrapper<>();
            jobQueryWrapper.eq("sid",dto.getSid());
            jobQueryWrapper.eq("cid",dto.getCid());
            JmJobDTO job = this.selectOne(jobQueryWrapper);
            //设置用户不能操作的属性
            try{
                jmJobDao.updateJob(dto);
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
    public CommonReturn delJob(List<String> sids, List<Integer> cids) {
        CommonReturn result = new CommonReturn();
        //判断长度是否相等
        if (sids!=null && cids!=null && sids.size()!=0 && cids.size()!=0 && sids.size()==cids.size()){
            for (int i = 0 ; i < sids.size() ; i++){
                QueryWrapper<JmJobEntity> jobQueryWrapper = new QueryWrapper<>();
                jobQueryWrapper.eq("sid",sids.get(i));
                jobQueryWrapper.eq("cid",cids.get(i));
                JmJobDTO jmJobDTO = this.selectOne(jobQueryWrapper);
                //查看计划单下是否有任务提交有的话不能删除
                QueryWrapper<JmJobRecEntity> jobRecQueryWrapper = new QueryWrapper<>();
                jobRecQueryWrapper.eq("jb_no",jmJobDTO.getJbNo());
                List<JmJobRecDTO> jmJobRecDTOs = jmJobRecService.select(jobRecQueryWrapper);
                if (jmJobRecDTOs==null || jmJobRecDTOs.size()==0){
                    try{
                        this.remove(jobQueryWrapper);
                        result.setAll(20000,null,"操作成功");
                    }catch (Exception e) {
                        result.setAll(10001, null, "操作失败");
                        return result;
                    }
                }else{
                    result.setAll(10001,null,"该计划单已经有随工单对其提交了,不能删除");
                }

            }

        }else{
            result.setAll(10001,null,"操作失败");
        }

        return result;
    }

    @Override
    public CommonReturn getJobPage(JmJobDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        List<JmJobDTO> jmJobDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmJobDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmJobDTOS,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn getJobJoinPage(JobJoin jobJoin) {
        CommonReturn result = new CommonReturn();
        if (jobJoin.getDescOrder()==null && jobJoin.getAscOrder()==null){
            jobJoin.setDescOrder("create_date");
        }
        if (jobJoin.getPage()==null){
            jobJoin.setPage(1);
        }
        if (jobJoin.getPageSize()==null){
            jobJoin.setPageSize(10);
        }
        PageHelper.startPage(jobJoin.getPage(), jobJoin.getPageSize());
        List<JobJoin> jobJoins = (List<JobJoin>) this.joinFindJobs(jobJoin).getData();
        for (JobJoin jobJoin1 : jobJoins){
            //查询产品规格
            QueryWrapper<JmPrdtEntity> jmPrdtQueryWrapper = new QueryWrapper<>();
            jmPrdtQueryWrapper.eq("prd_no",jobJoin1.getPrdNo());
            JmPrdtDTO prdtDTO = jmPrdtService.selectOne(jmPrdtQueryWrapper);
            if (prdtDTO!=null && prdtDTO.getPrdNo()!=null){
                jobJoin1.setSpc(prdtDTO.getSpc());
            }
        }
        PageInfo jobPages = new PageInfo<JobJoin>(jobJoins);
        result.setAll(20000,jobPages,"操作成功");
        return result;
    }


}
