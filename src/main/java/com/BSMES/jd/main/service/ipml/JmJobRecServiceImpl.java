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
        if (jobRec.getDescOrder()==null && jobRec.getAscOrder()==null){
            jobRec.setDescOrder("op_dd");
        }
        //驼峰转换
        if (jobRec.getAscOrder()!=null){
            jobRec.setAscOrder(MyUtils.humpToLine((String) jobRec.getAscOrder()));
        }else if(jobRec.getDescOrder()!=null){
            jobRec.setDescOrder(MyUtils.humpToLine((String) jobRec.getDescOrder()));
        }
        List<JobRec> jobRecs = jmJobRecDao.getJobRec(jobRec);
        for (JobRec job : jobRecs){
            QueryWrapper<JmJobRecBEntity> jmRecBQueryWrapper = new QueryWrapper<>();
            jmRecBQueryWrapper.eq("opsid",job.getOpsid());
            List<JmJobRecBDTO> jmJobRecB = jmJobRecBService.select(jmRecBQueryWrapper);
            BigDecimal sum = new BigDecimal("0");
            if (jmJobRecB!=null && jmJobRecB.size()>0){
                for (JmJobRecBDTO temp : jmJobRecB){
                    sum = sum.add(temp.getQtyOk());
                    //获取检验批号
                    job.setChkRmBn(temp.getChkRmBn());
                }
                job.setQtyOk(sum);
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
        CommonReturn result = new CommonReturn();
        Boolean flag = false;
        BigDecimal sum = new BigDecimal("0");
        JmJobRecDTO jmJobRecDTO = jobRecSave.getJmJobRecDTO();
        if (jobRecSave.getJmJobRecDTO()==null || jobRecSave.getJmJobRecDTO().getOpsid()==null || jobRecSave.getJmJobRecDTO().getOpsid().length()==0){
            jobRecSave.getJmJobRecDTO().setOpsid(this.getKey("JmJobRec","opsid",inssysvarService,jobRecSave.getJmJobRecDTO()));
            flag = true;
        }
        try{
            //保存随工单主表信息
            if (flag){
                this.insert(jobRecSave.getJmJobRecDTO());
            }else{
                this.edit(jobRecSave.getJmJobRecDTO());
            }
            //查询这张计划表的数据
            QueryWrapper<JmJobEntity> jmJobEntityQueryWrapper = new QueryWrapper<>();
            jmJobEntityQueryWrapper.eq("jb_no",jobRecSave.getJmJobRecDTO().getJbNo());
            //jmJobDTO.qty计划生产数量
            JmJobDTO jmJobDTO = jmJobService.selectOne(jmJobEntityQueryWrapper);

            //删除所有这张随工单的所有随工单明细表
            QueryWrapper<JmJobRecBEntity> jmJobRecBEntityQueryWrapper = new QueryWrapper<>();
            jmJobRecBEntityQueryWrapper.eq("opsid",jobRecSave.getJmJobRecDTO().getOpsid());
            jmJobRecBService.remove(jmJobRecBEntityQueryWrapper);
            //新增数据
            if (jobRecSave.getJmJobRecBDTOS()!=null && jobRecSave.getJmJobRecBDTOS().size()>0){
                jobRecSave.getJmJobRecBDTOS().stream().forEach(T->T.setOpsid(jobRecSave.getJmJobRecDTO().getOpsid()));
                jmJobRecBService.saveJobRecBs(jobRecSave.getJmJobRecBDTOS());
                result.setAll(20000,null,"操作成功");
            }
            //查询出计划单下所有的随工单
            QueryWrapper<JmJobRecEntity> jmJobRecEntityQueryWrapper = new QueryWrapper<>();
            jmJobRecEntityQueryWrapper.eq("jb_no",jmJobDTO.getJbNo());
            List<JmJobRecDTO> jmJobRecDTOS = this.select(jmJobRecEntityQueryWrapper);
            for (JmJobRecDTO jmJobRec : jmJobRecDTOS){
                //查询随工单的所有明细表
                QueryWrapper<JmJobRecBEntity> jmJobRecBEntityQueryWrapper1 = new QueryWrapper<>();
                jmJobRecBEntityQueryWrapper1.eq("opsid",jmJobRec.getOpsid());
                List<JmJobRecBDTO> jmJobRecBDTOS = jmJobRecBService.select(jmJobRecBEntityQueryWrapper1);
                for (JmJobRecBDTO jmJobRecBDTO : jmJobRecBDTOS){
                    if (jmJobRecBDTO.getQtyOk()!=null){
                        sum = sum.add(jmJobRecBDTO.getQtyOk());
                    }
                }

            }
            //总合格量大于等于计划数量
            if (sum.compareTo(jmJobDTO.getQty()) > -1){
                jmJobDTO.setState("94");
                jmJobService.editJob(jmJobDTO);
            }
        }catch (Exception e){
            result.setAll(10001,null,"操作失败");
            e.printStackTrace();
        }
        return result;
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
