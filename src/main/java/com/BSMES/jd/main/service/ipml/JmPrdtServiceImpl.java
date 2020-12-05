package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmPrdtDao;
import com.BSMES.jd.main.dto.JmPrdtDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmPrdtEntity;
import com.BSMES.jd.main.service.JmPrdtService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JmPrdtServiceImpl extends BaseServiceImpl<JmPrdtDao , JmPrdtEntity , JmPrdtDTO > implements JmPrdtService {
    @Override
    public void beforeInsert(JmPrdtDTO dto) {

    }

    @Override
    public void beforEedit(JmPrdtDTO dto) {

    }

    @DS("master")
    @Override
    public CommonReturn getPrdt(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmPrdtDTO> prdts = this.select(this.getQueryWrapper(dto));
        if(prdts.isEmpty()){
            result.setAll(20000,prdts,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,prdts,"查找成功");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn savePrdt(JmPrdtDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getPrdNo())){
            QueryWrapper<JmPrdtEntity> prdtQueryWrapper = new QueryWrapper<>();
            prdtQueryWrapper.eq("prd_no",dto.getPrdNo());
            JmPrdtDTO prdt = this.selectOne(prdtQueryWrapper);
            //判断 usrcode 是否重复
            if (prdt==null || prdt.getPrdNo()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"货品号已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn editPrdt(JmPrdtDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getPrdNo())){
            //获取原先的人员属性值
            QueryWrapper<JmPrdtEntity> prdtQueryWrapper = new QueryWrapper<>();
            prdtQueryWrapper.eq("prd_no",dto.getPrdNo());
            JmPrdtDTO worker = this.selectOne(prdtQueryWrapper);
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

    @DS("master")
    @Override
    public CommonReturn delPrdt(List<String> prdNos) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmPrdtEntity> prdtQueryWrapper = new QueryWrapper<>();
        prdtQueryWrapper.in("prd_no",prdNos);
        try{
            this.remove(prdtQueryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn getPrdtPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        QueryWrapper queryWrapper = new QueryWrapper();
        QueryWrapper queryWrapper1 = this.getQueryWrapper(dto);
        List<JmPrdtEntity> i = this.list(queryWrapper1);

        IPage<JmPrdtDTO> jmPrdtDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        if (jmPrdtDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmPrdtDTOS,"查找成功");
        }
        return result;
    }

    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();

        if(dto.getAscOrder()==null && dto.getDescOrder()==null){
            dto.setDescOrder("prd_no");
        }

        if (MyUtils.StringIsNull(dto.getSid())){
            queryWrapper.eq("prd_no",dto.getSid());
        }
        if (MyUtils.StringIsNull(dto.getPrdNo())){
            queryWrapper.like("prd_no",dto.getPrdNo());
        }
        if (MyUtils.StringIsNull(dto.getPrdName())){
            queryWrapper.like("name",dto.getPrdName());
        }
        if (MyUtils.StringIsNull(dto.getType())){
            queryWrapper.eq("knd",dto.getType());
        }
        if (MyUtils.StringIsNull(dto.getType())){
            queryWrapper.eq("knd",dto.getSorg());
        }
        if ("T".equals(dto.getNotType())){
            //没有停用的货品
            queryWrapper.isNull("nouse_dd");
        }else if("F".equals(dto.getNotType())){
            //找出停用的货品
            queryWrapper.isNotNull("nouse_dd");
        }

//        if (dto.getBegDd()!=null){
//            queryWrapper.ge("hpdate",dto.getBegDd());
//        }
//        if(dto.getEndDd()!=null){
//            queryWrapper.le("hpdate",dto.getEndDd());
//        }
        if (dto.getAscOrder()!=null){
            queryWrapper.orderByAsc(MyUtils.humpToLine((String) dto.getAscOrder()));
        }
        if (dto.getDescOrder()!=null && dto.getAscOrder()==null){
            queryWrapper.orderByDesc(MyUtils.humpToLine((String) dto.getDescOrder()));
        }


        return queryWrapper;
    }
}
