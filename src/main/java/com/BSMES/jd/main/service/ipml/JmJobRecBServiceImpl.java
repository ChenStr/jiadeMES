package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmJobRecBDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.JmJobRecBEntity;
import com.BSMES.jd.main.entity.JmJobRecEntity;
import com.BSMES.jd.main.entity.JmMouldEntity;
import com.BSMES.jd.main.service.JmJobRecBService;
import com.BSMES.jd.main.service.JmJobRecService;
import com.BSMES.jd.main.service.JmJobService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JmJobRecBServiceImpl extends BaseServiceImpl<JmJobRecBDao , JmJobRecBEntity , JmJobRecBDTO> implements JmJobRecBService {

    @Autowired
    JmJobRecService jmJobRecService;

    @Autowired
    JmJobRecBDao jmJobRecBDao;

    @Autowired
    HttpServletResponse response;

    @Override
    public void beforeInsert(JmJobRecBDTO dto) {

    }

    @Override
    public void beforEedit(JmJobRecBDTO dto) {

    }

    @Override
    public CommonReturn getJobRecB(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmJobRecBDTO> jobRecBs = this.select(this.getQueryWrapper(dto));
        if(jobRecBs.isEmpty()){
            result.setAll(20000,jobRecBs,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,jobRecBs,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveJobRecB(JmJobRecBDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 md_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getOpsid()) && dto.getCid()!=null ){
            QueryWrapper<JmJobRecBEntity> jobRecBQueryWrapper = new QueryWrapper<>();
            jobRecBQueryWrapper.eq("opsid",dto.getOpsid());
            jobRecBQueryWrapper.eq("cid",dto.getCid());
            JmJobRecBDTO jobRecB = this.selectOne(jobRecBQueryWrapper);
            //判断是否真的有opsid,存在
            QueryWrapper<JmJobRecEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("opsid",dto.getOpsid());
            JmJobRecDTO jmJobRecDTO = jmJobRecService.selectOne(queryWrapper);
            //判断 opsid 是否重复
            if ((jmJobRecDTO!=null && jmJobRecDTO.getOpsid()!=null) && (jobRecB==null || jobRecB.getOpsid()==null)){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"单号已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn editJobRecB(JmJobRecBDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 md_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getOpsid()) && dto.getCid()!=null ){
            //获取原先的人员属性值
            QueryWrapper<JmJobRecBEntity> jobRecBQueryWrapper = new QueryWrapper<>();
            jobRecBQueryWrapper.eq("opsid",dto.getOpsid());
            jobRecBQueryWrapper.eq("cid",dto.getCid());
            JmJobRecBDTO jobRecB = this.selectOne(jobRecBQueryWrapper);
            //设置用户不能操作的属性
            try{
//                this.edit(dto);
                jmJobRecBDao.updateJobRecB(dto);
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

    @Transactional
    @Override
    public CommonReturn editJobRecBs(List<JmJobRecBDTO> dtos) {
        CommonReturn result = new CommonReturn();
        for (JmJobRecBDTO dto : dtos){
            if (dto!=null && MyUtils.StringIsNull(dto.getOpsid()) && dto.getCid()!=null ){
                //获取原先的人员属性值
                QueryWrapper<JmJobRecBEntity> jobRecBQueryWrapper = new QueryWrapper<>();
                jobRecBQueryWrapper.eq("opsid",dto.getOpsid());
                jobRecBQueryWrapper.eq("cid",dto.getCid());
                JmJobRecBDTO jobRecB = this.selectOne(jobRecBQueryWrapper);
                //设置用户不能操作的属性
                try{
//                this.edit(dto);
                    jmJobRecBDao.updateJobRecB(dto);
                    result.setAll(20000,null,"操作成功");
                }catch (Exception e){
                    result.setAll(10001,null,"操作失败");
                    e.printStackTrace();
                    return result;
                }
            }else{
                result.setAll(10001,null,"参数错误");
            }
        }
        return result;
    }

    @Override
    public CommonReturn delJobRecB(List<String> opsids, List<Integer> cids) {
        CommonReturn result = new CommonReturn();
        //判断长度是否相等
        if (opsids!=null && cids!=null && opsids.size()!=0 && cids.size()!=0 && opsids.size()==cids.size()){
            for (int i = 0 ; i < opsids.size() ; i++){
                QueryWrapper<JmJobRecBEntity> jobRecBQueryWrapper = new QueryWrapper<>();
                jobRecBQueryWrapper.eq("opsid",opsids.get(i));
                jobRecBQueryWrapper.eq("cid",cids.get(i));
                JmJobRecBDTO jmJobRecBDTO = this.selectOne(jobRecBQueryWrapper);
                try{
                    this.remove(jobRecBQueryWrapper);
                    result.setAll(20000,null,"操作成功");
                }catch (Exception e) {
                    result.setAll(10001, null, "操作失败");
                    return result;
                }
            }
        }else{
            result.setAll(10001,null,"操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn saveJobRecBs(List<JmJobRecBDTO> dtos) {
        CommonReturn result = new CommonReturn();
        for (JmJobRecBDTO dto : dtos){
            if (dto!=null && MyUtils.StringIsNull(dto.getOpsid()) && dto.getCid()!=null ) {
                QueryWrapper<JmJobRecBEntity> jobRecBQueryWrapper = new QueryWrapper<>();
                jobRecBQueryWrapper.eq("opsid", dto.getOpsid());
                jobRecBQueryWrapper.eq("cid", dto.getCid());
                JmJobRecBDTO jobRecB = this.selectOne(jobRecBQueryWrapper);
                dto.setOpDd(new Date());
            }
        }
        jmJobRecBDao.insertJobRecBs(dtos);
        result.setAll(20000,null,"操作成功");
        return result;
    }

    @Override
    public CommonReturn getJobRecBPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<JmJobRecBDTO> jmJobRecBDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        if (jmJobRecBDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmJobRecBDTOS,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn getJobRecReportPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        if (dto.getPage()==null){
            dto.setPage(1);
        }
        if (dto.getPageSize()==null){
            dto.setPageSize(10);
        }
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        List<Report> jmJobRecBDTOS = jmJobRecBDao.getJobRecReport(dto);

        PageInfo pageInfo = new PageInfo<Report>(jmJobRecBDTOS);

        result.setAll(20000,pageInfo,"操作成功");
        return result;
    }

    /**
     * 车间生产日报表 Excel 导出
     * @param dto
     * @return
     */
    @Override
    public CommonReturn getSorgDayReportExcel(ResultType dto) {
        CommonReturn result = new CommonReturn();
        try{
            List<Report> jmJobRecBDTOS = jmJobRecBDao.getJobRecReport(dto);
            //计算总数量
            Report report = new Report();
            BigDecimal sum = new BigDecimal("0");
            for (Report jmJobRecBDTO: jmJobRecBDTOS){
                sum = sum.add(jmJobRecBDTO.getQty());
            }

            report.setQty(sum);
            HashMap<String,String> map = new HashMap<>();
            map.put("dep","车间名称");
            map.put("sid","计划单号");
            map.put("prdNo","产品代号");
            map.put("prdName","产品名称");
            map.put("qty","数量");
            String fileName = "车间生产日报表.xlsx";
            jmJobRecBDTOS.add(report);
            MyUtils.exportExcel(jmJobRecBDTOS,map,fileName,response);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(20000,null,"操作成功");
//            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CommonReturn getJobRecMonReport(ResultType dto) {
        CommonReturn result = new CommonReturn();
        if (dto.getPage()==null){
            dto.setPage(1);
        }
        if (dto.getPageSize()==null){
            dto.setPageSize(10);
        }
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        List<Report> jmJobRecBDTOS = jmJobRecBDao.getJobRecMonReport(dto);

        PageInfo pageInfo = new PageInfo<Report>(jmJobRecBDTOS);

        result.setAll(20000,pageInfo,"操作成功");
        return result;
    }

    @Override
    public CommonReturn getUserMonReportExcel(ResultType dto) {
        CommonReturn result = new CommonReturn();
        try{
            List<Report> jmJobRecBDTOS = jmJobRecBDao.getJobRecMonReport(dto);
            HashMap<String,String> map = new HashMap<>();
            map.put("dep","车间名称");
            map.put("wkNo","人员代号");
            map.put("wkName","人员名称");
            map.put("prdNo","产品代号");
            map.put("prdName","产品名称");
            map.put("qty","数量");
            String fileName = "人员生产月生产报表.xlsx";
            MyUtils.exportExcel(jmJobRecBDTOS,map,fileName,response);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(20000,null,"操作成功");
//            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CommonReturn getJobRecRsNoMonReport(ResultType dto) {
        CommonReturn result = new CommonReturn();
        if (dto.getPage()==null){
            dto.setPage(1);
        }
        if (dto.getPageSize()==null){
            dto.setPageSize(10);
        }
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        List<Report> jmJobRecBDTOS = jmJobRecBDao.getJobRecRsNoMonReport(dto);

        PageInfo pageInfo = new PageInfo<Report>(jmJobRecBDTOS);

        result.setAll(20000,pageInfo,"操作成功");
        return result;
    }

    @Override
    public CommonReturn getDevMonReportExcel(ResultType dto) {
        CommonReturn result = new CommonReturn();
        try{
            List<Report> jmJobRecBDTOS = jmJobRecBDao.getJobRecRsNoMonReport(dto);
            HashMap<String,String> map = new HashMap<>();
            map.put("devNo","设备代号");
            map.put("devName","设备名称");
            map.put("prdNo","产品代号");
            map.put("prdName","产品名称");
            map.put("qty","数量");
            String fileName = "设备生产月报表.xlsx";
            MyUtils.exportExcel(jmJobRecBDTOS,map,fileName,response);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(20000,null,"操作成功");
//            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }


    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();

        if(dto.getAscOrder()==null && dto.getDescOrder()==null){
            dto.setDescOrder("op_dd");
        }

        if (MyUtils.StringIsNull(dto.getSid())){
            queryWrapper.eq("opsid",dto.getSid());
        }
        if (dto.getBegDd()!=null){
            queryWrapper.ge("op_dd",dto.getBegDd());
        }
        if(dto.getEndDd()!=null){
            queryWrapper.le("op_dd",dto.getEndDd());
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
