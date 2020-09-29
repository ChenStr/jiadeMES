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

    @Override
    public void beforeInsert(JmJobDTO dto) {

    }

    @Override
    public void beforEedit(JmJobDTO dto) {

    }

    @Override
    public CommonReturn getJob(JmJobDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto);
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
        List<JobJoin> jobJoins = jmJobDao.joinFindJob(jobJoin);
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

    @Override
    public CommonReturn saveJobs(List<JmJobDTO> dtos) {
        CommonReturn result = new CommonReturn();
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
            }
        }
        try{
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
            dto.setJbNo(job.getJbNo());
            try{
                jmJobDao.updateJob(dto);
//                this.edit(dto);
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
        if (jobJoin.getPage()==null){
            jobJoin.setPage(1);
        }
        if (jobJoin.getPageSize()==null){
            jobJoin.setPageSize(10);
        }
        PageHelper.startPage(jobJoin.getPage(), jobJoin.getPageSize());
        List<JobJoin> jobJoins = (List<JobJoin>) this.joinFindJobs(jobJoin).getData();
        PageInfo jobPages = new PageInfo<JobJoin>(jobJoins);
        result.setAll(20000,jobPages,"操作成功");
        return result;
    }


}
