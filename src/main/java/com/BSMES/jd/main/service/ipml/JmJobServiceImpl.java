package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmJobDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.dto.erp.ErpMfMoDTO;
import com.BSMES.jd.main.dto.erp.ErpTfMoDTO;
import com.BSMES.jd.main.entity.*;
import com.BSMES.jd.main.service.*;
import com.BSMES.jd.main.service.erp.ErpMfMoService;
import com.BSMES.jd.main.service.erp.ErpTfMoService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class JmJobServiceImpl extends BaseServiceImpl<JmJobDao , JmJobEntity , JmJobDTO> implements JmJobService {

    @Autowired
    HttpServletResponse response;

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

    @Autowired
    JmBsDictionaryService jmBsDictionaryService;

    @Autowired
    ErpMfMoService erpMfMoService;

    @Autowired
    ErpTfMoService erpTfMoService;

    @Value("${excel.address}")
    private String address;


    @Override
    public void beforeInsert(JmJobDTO dto) {

    }

    @Override
    public void beforEedit(JmJobDTO dto) {

    }

    @DS("master")
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

    @DS("master")
    @Override
    public CommonReturn joinFindJobs(JobJoin jobJoin) {
        CommonReturn result = new CommonReturn();
        //驼峰转换
        if (jobJoin.getAscOrder()!=null){
            jobJoin.setAscOrder(MyUtils.humpToLine((String) jobJoin.getAscOrder()));
        }
        if(jobJoin.getDescOrder()!=null){
            jobJoin.setDescOrder(MyUtils.humpToLine((String) jobJoin.getDescOrder()));
        }
        jobJoin.setAscOrder(MyUtils.humpToLine(jobJoin.getAscOrder().toString()));
        jobJoin.setDescOrder(MyUtils.humpToLine(jobJoin.getDescOrder().toString()));
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
            for (int i=0; i < jmBomTfDTO.size(); i++){
                if (jmBomTfDTO.get(i)!=null && jmBomTfDTO.get(i).getPrdNo()!=null){
                    prdNos.add(jmBomTfDTO.get(i).getPrdNo());
                }
            }
            QueryWrapper<JmPrdtEntity> jmPrdtEntityQueryWrapper = new QueryWrapper<>();
            jmPrdtEntityQueryWrapper.in("prd_no",prdNos);
            if (prdNos!=null && prdNos.size()>0){
                List<JmPrdtDTO> prdtDTOS = jmPrdtService.select(jmPrdtEntityQueryWrapper);
                if (prdtDTOS!=null && prdtDTOS.size()>0){
                    jobJoin1.setPrdts(prdtDTOS);
                }
            }
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
                            if(jmJobRecBDTO.getQtyOk()!=null){
                                sum = sum.add(jmJobRecBDTO.getQtyOk());
                            }

                        }
                    }
                }
            }
            jobJoin1.setQtyAlready(sum);
        }
        result.setAll(20000,jobJoins,"操作成功");
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn findJob(JobJoin jobJoin) {
        CommonReturn result = new CommonReturn();
        try{
            if(jobJoin.getDescOrder()!=null){
                jobJoin.setDescOrder(MyUtils.humpToLine(jobJoin.getDescOrder().toString()));
            }
            if(jobJoin.getAscOrder()!=null){
                jobJoin.setAscOrder(MyUtils.humpToLine(jobJoin.getAscOrder().toString()));
            }
            List<JobJoin> jobJoins = jmJobDao.findJob(jobJoin);
            result.setAll(20000,jobJoins,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn findJobPage(JobJoin jobJoin) {
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
        List<JobJoin> jobJoins = (List<JobJoin>) this.findJob(jobJoin).getData();
        PageInfo jobPages = new PageInfo<JobJoin>(jobJoins);
        result.setAll(20000,jobPages,"操作成功");
        return result;
    }

    @DS("master")
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

    @DS("master")
//    @Transactional
    @Override
    public CommonReturn saveJobs(MoSave jobSave) {
        CommonReturn result = new CommonReturn();

        List<JmJobDTO> dtos = jobSave.getJmJobDTOS();
        BigDecimal sum = new BigDecimal("0");
        //将数据格式补充完毕
        if (dtos!=null && dtos.size()>0){
            for (int i=0 ; i < dtos.size() ; i++){
                if(dtos.get(i).getJbNo()==null || dtos.get(i).getJbNo().length()<0 ){
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
                }
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
                //修改调度单的状态
                if (jmMoMfDTO.getQty().compareTo(sum) < 1){
                    QueryWrapper<JmBsDictionaryEntity> jmBsDictionaryEntityQueryWrapper = new QueryWrapper<>();
                    jmBsDictionaryEntityQueryWrapper.eq("id","DIS20201030013");
                    JmBsDictionaryDTO jmBsDictionaryDTO = jmBsDictionaryService.selectOne(jmBsDictionaryEntityQueryWrapper);
                    jmMoMfDTO.setState(Integer.valueOf(jmBsDictionaryDTO.getCode()));
                }else{
                    QueryWrapper<JmBsDictionaryEntity> jmBsDictionaryEntityQueryWrapper = new QueryWrapper<>();
                    jmBsDictionaryEntityQueryWrapper.eq("id","DIS20201030012");
                    JmBsDictionaryDTO jmBsDictionaryDTO = jmBsDictionaryService.selectOne(jmBsDictionaryEntityQueryWrapper);
                    jmMoMfDTO.setState(Integer.valueOf(jmBsDictionaryDTO.getCode()));
                }
                jmMoMfService.editMoMf(jmMoMfDTO);
            }
            //将 sid 补全
            for (JmJobDTO jmJobDTO : jobSave.getJmJobDTOS()){
                jmJobDTO.setSid(jobSave.getJmMoMfDTO().getSid());
            }
            jmJobDao.insertJmJobs(dtos);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(10001,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @DS("master")
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
                isComplete(dto);
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

    @DS("master")
    @Override
    public CommonReturn getJobPage(JmJobDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        IPage<JmJobDTO> jmJobDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmJobDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmJobDTOS,"查找成功");
        }
        return result;
    }

    @DS("master")
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

    @DS("master")
    @Override
    public CommonReturn getJmJobReport(ResultType dto) {
        CommonReturn result = new CommonReturn();
        if (dto.getPage()==null){
            dto.setPage(1);
        }
        if (dto.getPageSize()==null){
            dto.setPageSize(10);
        }
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        List<Report> jmJobRecBDTOS = jmJobDao.getJmJobReport(dto);

        PageInfo pageInfo = new PageInfo<Report>(jmJobRecBDTOS);

        result.setAll(20000,pageInfo,"操作成功");
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn exportExcel(ResultType dto) {
        CommonReturn result = new CommonReturn();
        try{
            List<Report> jmJobRecBDTOS = jmJobDao.getJmJobReport(dto);
            LinkedHashMap<String,String> map = new LinkedHashMap<>();
            map.put("dep","车间名称");
            map.put("prdNo","产品代号");
            map.put("prdName","产品名称");
            map.put("dqty","订单数量");
            map.put("jqty","计划数量");
            map.put("wqty","计划完成数");
            map.put("sqty","当月完成计划数");
            String fileName = "车间生产月报表.xlsx";

            HashMap<String,Object> map2 = new HashMap<>();

            if (dto.getDep()!=null){
                map2.put("sorg",dto.getDep());
            }
            if (dto.getBegDd()!=null){
                map2.put("time",dto.getBegDd().toString());
            }

            map2.put("title","车间生产月报表");
            map2.put("address",this.address);

            MyUtils.exportExcel(jmJobRecBDTOS,map,fileName,response,map2);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(20000,null,"操作成功");
//            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CommonReturn getsorgSum(ResultType dto) {
        CommonReturn result = new CommonReturn();
        try{
            if (dto.getPage()==null){
                dto.setPage(1);
            }
            if (dto.getPageSize()==null){
                dto.setPageSize(10);
            }
            PageHelper.startPage(dto.getPage(), dto.getPageSize());
            List<Report> reports = jmJobDao.getsorgSum(dto);
            PageInfo reportPageInfo = new PageInfo<Report>(reports);
            result.setAll(20000,reportPageInfo,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CommonReturn getsorgYield(ResultType dto) {
        CommonReturn result = new CommonReturn();
        try{
            if (dto.getPage()==null){
                dto.setPage(1);
            }
            if (dto.getPageSize()==null){
                dto.setPageSize(10);
            }
            PageHelper.startPage(dto.getPage(), dto.getPageSize());
            List<Report> reports = jmJobDao.getsorgYield(dto);
            PageInfo reportPageInfo = new PageInfo<>(reports);
            result.setAll(20000,reportPageInfo,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CommonReturn getGood(ResultType dto) {
        CommonReturn result = new CommonReturn();
        try{
            List<Report> reports =  jmJobDao.getGood(dto);
            result.setAll(20000,reports,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 计划单被修改时触发的方法
     * @param dto 计划单
     */
    public void isComplete(JmJobDTO dto){
        //判断计划单的状态是否被修改为已完成
        QueryWrapper<JmBsDictionaryEntity> jmBsDictionaryDTOQueryWrapper = new QueryWrapper<>();
        jmBsDictionaryDTOQueryWrapper.eq("id","DIS20201030015");
        JmBsDictionaryDTO jmBsDictionaryDTO = jmBsDictionaryService.selectOne(jmBsDictionaryDTOQueryWrapper);
        //已中止的状态查询
        QueryWrapper<JmBsDictionaryEntity> jmBsDictionaryDTOQueryWrapper2 = new QueryWrapper<>();
        jmBsDictionaryDTOQueryWrapper2.eq("id","DIS20201030014");
        JmBsDictionaryDTO jmBsDictionaryDTO2 = jmBsDictionaryService.selectOne(jmBsDictionaryDTOQueryWrapper2);
        if (dto.getState().equals(jmBsDictionaryDTO.getCode()) || dto.getState().equals(jmBsDictionaryDTO2.getCode())){
            //判断其他计划单是否也时已完成的状态
            QueryWrapper<JmMoMfEntity> jmMoMfEntityQueryWrapper = new QueryWrapper<>();
            jmMoMfEntityQueryWrapper.eq("sid",dto.getSid());
            JmMoMfDTO jmMoMfDTO = jmMoMfService.selectOne(jmMoMfEntityQueryWrapper);
            if (jmMoMfDTO!=null && jmMoMfDTO.getSid()!=null){
                //查询其他的计划单
                JobJoin jobJoin = new JobJoin();
                jobJoin.setSid(dto.getSid());
                List<JobJoin> jobJoins = jmJobDao.findJob(jobJoin);
                //判断其他的计划单是否都已经完成了
                Boolean flag = true;
                BigDecimal sum = new BigDecimal("0");
                for (JobJoin join : jobJoins){
                    //是否完工
                    Boolean iscomplete = join.getState().equals(jmBsDictionaryDTO.getCode()) || join.getState().equals(jmBsDictionaryDTO2.getCode());


                    if (!iscomplete){
                        flag = false;
                        sum = sum.add(join.getQtyPlan());
                    }else{
                        sum = sum.add(join.getQtyAlready());
                    }
                }
                //修改时间
                if(flag == true){
                    QueryWrapper<JmBsDictionaryEntity> jmBsDictionaryDTOQueryWrapper1 = new QueryWrapper<>();
                    jmBsDictionaryDTOQueryWrapper1.eq("id","DIS20210112002");
                    JmBsDictionaryDTO jmBsDictionaryDTO1 = jmBsDictionaryService.selectOne(jmBsDictionaryDTOQueryWrapper1);
                    int day = Integer.parseInt(jmBsDictionaryDTO1.getCode());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + day);
                    Date date = calendar.getTime();
                    jmMoMfDTO.setStaDd(date);
                    jmMoMfService.editMoMf(jmMoMfDTO);
                }
                //修改 ERP 调度单
                ResultType resultType = new ResultType();
                resultType.setSid(jmMoMfDTO.getSid());
                ErpMfMoDTO erpMfMoDTO = new ErpMfMoDTO();
                erpMfMoDTO.setMO_NO(jmMoMfDTO.getSid());
                erpMfMoDTO.setQTY(sum);
                ErpTfMoDTO erpTfMoDTO = new ErpTfMoDTO();
                erpTfMoDTO.setMO_NO(dto.getSid());
                erpTfMoDTO.setQTY_STD(sum);
                erpTfMoDTO.setQTY_RSV(sum);
                erpMfMoService.editMfMo(erpMfMoDTO);
                erpTfMoService.editTfMo(erpTfMoDTO);
            }
        }
    }



}
