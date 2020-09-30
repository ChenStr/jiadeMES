package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmJobRecDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.JmJobEntity;
import com.BSMES.jd.main.entity.JmJobRecBEntity;
import com.BSMES.jd.main.entity.JmJobRecEntity;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.main.service.JmJobRecBService;
import com.BSMES.jd.main.service.JmJobRecService;
import com.BSMES.jd.main.service.JmJobService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class JmJobRecServiceImpl extends BaseServiceImpl<JmJobRecDao , JmJobRecEntity , JmJobRecDTO> implements JmJobRecService {


    @Autowired
    JmJobService jmJobService;

    @Autowired
    JmJobRecBService jmJobRecBService;

    @Autowired
    InssysvarService inssysvarService;


    @Autowired
    JmJobRecDao jmJobRecDao;

    @Override
    public void beforeInsert(JmJobRecDTO dto) {

    }

    @Override
    public void beforEedit(JmJobRecDTO dto) {

    }

    @Override
    public CommonReturn getJobRec(JmJobRecDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto);
        List<JmJobRecDTO> jobRec = this.select(data);
        if(jobRec.isEmpty()){
            result.setAll(20000,jobRec,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,jobRec,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn getJobRecs(JobRec jobRec) {
        CommonReturn result = new CommonReturn();
        List<JobRec> jobRecs = jmJobRecDao.getJobRec(jobRec);
        for (JobRec job : jobRecs){
            QueryWrapper<JmJobRecBEntity> jmRecBQueryWrapper = new QueryWrapper<>();
            jmRecBQueryWrapper.eq("opsid",jobRec.getOpsid());
            List<JmJobRecBDTO> jmJobRecB = jmJobRecBService.select(jmRecBQueryWrapper);
            BigDecimal sum = new BigDecimal("0");;
            if (jmJobRecB!=null && jmJobRecB.size()>0){
                for (JmJobRecBDTO temp : jmJobRecB){
                    sum.add(temp.getQtyOk());
                    //获取检验批号
                    job.setChkRmBn(temp.getChkRmBn());
                }
            }
            //设置首尾模
            if (job.getStatePre().equals("1")){
                job.setHeader(true);
                job.setTail(false);
            }
            else if(job.getStatePre().equals("2")){
                job.setTail(true);
                job.setHeader(false);
            }else if(job.getStatePre().equals("3")){
                job.setTail(true);
                job.setHeader(true);
            }else{
                job.setHeader(false);
                job.setTail(false);
            }
            QueryWrapper<JmJobRecBEntity> jmJobRecBEntityQueryWrapper = new QueryWrapper<>();
            jmJobRecBEntityQueryWrapper.eq("opsid",job.getOpsid());
            List<JmJobRecBDTO> jmJobRecBDTO = jmJobRecBService.select(jmJobRecBEntityQueryWrapper);
            job.setJobRecB(jmJobRecBDTO);
            job.setQtyOk(sum);
        }
        result.setAll(20000,jobRecs,"操作成功");
        return result;
    }

    @Transactional
    @Override
    public CommonReturn saveJobRecAndRecB(JobRecSave jobRecSave) {
        JmJobRecDTO jmJobRecDTO = jobRecSave.getJmJobRecDTO();
        List<JmJobRecBDTO> jmJobRecBDTOS = jobRecSave.getJmJobRecBDTOS();
        if (jmJobRecDTO.getOpsid()!=null){
            this.saveJobRec(jmJobRecDTO);
        }

//        jmJobRecBService.saveJobRecBs()
        return null;
    }


    public CommonReturn saveJobRec(JmJobRecDTO dto) {
        CommonReturn result = new CommonReturn();
        dto.setOpsid(this.getKey("JmJobRec","opsid",inssysvarService,dto));
        //判断dto是否为空 判断dto的 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getOpsid())){
            QueryWrapper<JmJobRecEntity> jobRecQueryWrapper = new QueryWrapper<>();
            jobRecQueryWrapper.eq("opsid",dto.getOpsid());
            JmJobRecDTO jobRec = this.selectOne(jobRecQueryWrapper);
            //判断计划单号是否存在
            QueryWrapper<JmJobEntity> jmJobQueryWrapper = new QueryWrapper<>();
            jmJobQueryWrapper.eq("jb_no",dto.getJbNo());
            JmJobDTO jmJobDTO = jmJobService.selectOne(jmJobQueryWrapper);
            //判断 opsid 是否重复
            if ( (jmJobDTO!=null && jmJobDTO.getJbNo()!=null) &&(jobRec==null || jobRec.getOpsid()==null)){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"随工单号已经存在或计划单号不存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn editJobRec(JmJobRecDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getOpsid())){
            //获取原先的用户属性值
            QueryWrapper<JmJobRecEntity> jmJobRecQueryWrapper = new QueryWrapper<>();
            jmJobRecQueryWrapper.eq("opsid",dto.getOpsid());
            JmJobRecDTO var = this.selectOne(jmJobRecQueryWrapper);
            //设置用户不能操作的属性
            dto.setJbNo(var.getJbNo());
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
    public CommonReturn delJobRec(List<String> opsids) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmJobRecEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("opsid",opsids);
        //查看本张随工单下是否有明细表
        QueryWrapper<JmJobRecBEntity> jmJobRecBEntityQueryWrapper = new QueryWrapper<>();
        jmJobRecBEntityQueryWrapper.in("opsid",opsids);
        List<JmJobRecBDTO> jmJobRecBDTOS = jmJobRecBService.select(jmJobRecBEntityQueryWrapper);
        if (jmJobRecBDTOS==null || jmJobRecBDTOS.size()==0){
            try{
                this.remove(queryWrapper);
                result.setAll(20000,null,"操作成功");
            }catch (Exception e) {
                result.setAll(10001, null, "操作失败");
            }
        }else{
            result.setAll(10001, null, "本随工单下已经有明细了不能删除");
        }

        return result;
    }

    @Override
    public CommonReturn getJobRecPage(JmJobRecDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        List<JmJobRecDTO> jmJobRecDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmJobRecDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmJobRecDTOS,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn getJobRecsPage(JobRec jobRec) {
        CommonReturn result = new CommonReturn();
        if (jobRec.getPage()==null){
            jobRec.setPage(1);
        }
        if (jobRec.getPageSize()==null){
            jobRec.setPageSize(10);
        }
        PageHelper.startPage(jobRec.getPage(), jobRec.getPageSize());
        List<JobRec> jobRecs = (List<JobRec>) this.getJobRecs(jobRec).getData();
        for (JobRec job:jobRecs){
            //设置首尾模
            if (job.getStatePre().equals("1")){
                job.setHeader(true);
                job.setTail(false);
            }
            else if(job.getStatePre().equals("2")){
                job.setTail(true);
                job.setHeader(false);
            }else if(job.getStatePre().equals("3")){
                job.setTail(true);
                job.setHeader(true);
            }else{
                job.setHeader(false);
                job.setTail(false);
            }
            QueryWrapper<JmJobRecBEntity> jmJobRecBEntityQueryWrapper = new QueryWrapper<>();
            jmJobRecBEntityQueryWrapper.eq("opsid",job.getOpsid());
            List<JmJobRecBDTO> jmJobRecBDTO = jmJobRecBService.select(jmJobRecBEntityQueryWrapper);
            job.setJobRecB(jmJobRecBDTO);
        }
        PageInfo jobPages = new PageInfo<JobRec>(jobRecs);
        result.setAll(20000,jobPages,"操作成功");
        return result;
    }
}
