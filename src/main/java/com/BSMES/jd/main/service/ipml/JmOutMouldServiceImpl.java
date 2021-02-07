package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmOutMouldDao;
import com.BSMES.jd.main.dto.JmOutMouldDTO;
import com.BSMES.jd.main.dto.Report;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmOutMouldEntity;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.main.service.JmOutMouldService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class JmOutMouldServiceImpl extends BaseServiceImpl<JmOutMouldDao, JmOutMouldEntity, JmOutMouldDTO> implements JmOutMouldService {


    @Autowired
    InssysvarService inssysvarService;

    @Autowired
    HttpServletResponse response;

    @Value("${excel.address}")
    private String address;

    @Override
    public void beforeInsert(JmOutMouldDTO dto) {

    }

    @Override
    public void beforEedit(JmOutMouldDTO dto) {

    }

    @Override
    public CommonReturn getOut(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmOutMouldDTO> jmOutMouldDTOS = this.select(this.getQueryWrapper(dto));
        if(jmOutMouldDTOS.isEmpty()){
            result.setAll(20000,jmOutMouldDTOS,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,jmOutMouldDTOS,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveOut(JmOutMouldDTO dto) {
        CommonReturn result = new CommonReturn();
        if (dto.getSid()==null){
            dto.setSid(getKey("Out","sid",inssysvarService,dto));
        }
        //判断dto是否为空 判断dto的usrcode是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            QueryWrapper<JmOutMouldEntity> jmOutMouldEntityQueryWrapper = new QueryWrapper<>();
            jmOutMouldEntityQueryWrapper.eq("sid",dto.getSid());
            JmOutMouldDTO jmOutMouldDTO = this.selectOne(jmOutMouldEntityQueryWrapper);
            //判断 usrcode 是否重复
            if (jmOutMouldDTO==null || jmOutMouldDTO.getSid()==null){
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
    public CommonReturn editOut(JmOutMouldDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            //获取原先的用户属性值
            QueryWrapper<JmOutMouldEntity> jmOutMouldEntityQueryWrapper = new QueryWrapper<>();
            jmOutMouldEntityQueryWrapper.eq("sid",dto.getSid());
            JmOutMouldDTO var = this.selectOne(jmOutMouldEntityQueryWrapper);
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
    public CommonReturn delOut(List<String> sids) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmOutMouldEntity> queryWrapper = new QueryWrapper<>();
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
    public CommonReturn getOutExcel(ResultType dto) {
        CommonReturn result = new CommonReturn();
        try{
            List<JmOutMouldDTO> jmOutMouldDTOS = this.select(this.getQueryWrapper(dto));
            LinkedHashMap<String,String> map = new LinkedHashMap<>();
            SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            jmOutMouldDTOS.stream().forEach(T->T.setTime(time.format(T.getUpdateTime())));
            map.put("sid","单号");
            map.put("rsNo","机台号");
            map.put("mdNo","模具代号");
            map.put("prdNo","产品代号");
            map.put("prdName","产品名称");
            map.put("allMdNumber","全模具数");
            map.put("outMdNumber","出模具数");
            map.put("mdMan","模具负责人");
            map.put("smake","记录人");
            map.put("dep","部门代号");
            map.put("time","更新时间");
            map.put("updator","更新人");
            String fileName = "出模率明细报表.xlsx";

            HashMap<String,Object> map2 = new HashMap<>();

            if (dto.getDep()!=null){
                map2.put("sorg",dto.getDep());
            }

            java.util.Date date = new Date();
            map2.put("time",date.toString());


            map2.put("title","出模率明细报表");
//            map2.put("address",this.address);

            MyUtils.exportExcel(jmOutMouldDTOS,map,fileName,response,map2);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(20000,null,"操作成功");
//            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CommonReturn getOutPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<JmOutMouldDTO> jmOutMouldDTOIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        if (jmOutMouldDTOIPage==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmOutMouldDTOIPage,"查找成功");
        }
        return result;
    }

    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();

        if (MyUtils.StringIsNull(dto.getSid())){
            queryWrapper.eq("sid",dto.getSid());
        }
        if (MyUtils.StringIsNull(dto.getDevNo())){
            queryWrapper.eq("rs_no",dto.getDevNo());
        }
        if (MyUtils.StringIsNull(dto.getMouldNo())){
            queryWrapper.like("md_no",dto.getMouldNo());
        }
        if (MyUtils.StringIsNull(dto.getPrdNo())){
            queryWrapper.eq("prd_no",dto.getPrdNo());
        }
        if (MyUtils.StringIsNull(dto.getPrdName())){
            queryWrapper.eq("prd_name",dto.getPrdName());
        }
        if (MyUtils.StringIsNull(dto.getDep())){
            queryWrapper.like("dep",dto.getDep());
        }
        if (dto.getBegDd()!=null){
            queryWrapper.ge("create_time",dto.getBegDd());
        }
        if(dto.getEndDd()!=null){
            queryWrapper.le("create_time",dto.getEndDd());
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
