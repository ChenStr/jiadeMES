package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmDevDao;
import com.BSMES.jd.main.dao.JmDevSalDao;
import com.BSMES.jd.main.dto.JmChkstdTfDTO;
import com.BSMES.jd.main.dto.JmDevSalDTO;
import com.BSMES.jd.main.dto.JmMoMfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmChkstdTfEntity;
import com.BSMES.jd.main.entity.JmDevSalEntity;
import com.BSMES.jd.main.service.JmDevSalService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JmDevSalServiceImpl extends BaseServiceImpl<JmDevSalDao , JmDevSalEntity , JmDevSalDTO> implements JmDevSalService {

    @Autowired
    JmDevSalDao jmDevSalDao;

    @Override
    public void beforeInsert(JmDevSalDTO dto) {
        dto.setHpdate(new Date());
    }

    @Override
    public void beforEedit(JmDevSalDTO dto) {

    }

    @DS("master")
    @Override
    public CommonReturn getDevSal(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmDevSalDTO> dtos = this.select(this.getQueryWrapper(dto));
        if(dtos.isEmpty()){
            result.setAll(20000,dtos,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,dtos,"查找成功");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn saveDevSal(JmDevSalDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的usrcode是否有值
        if (dto!=null){
            QueryWrapper<JmDevSalEntity> jmDevSalEntityQueryWrapper = new QueryWrapper<>();
            jmDevSalEntityQueryWrapper.eq("dev_no",dto.getDevNo()).eq("sal_no",dto.getSalNo());
            JmDevSalDTO jmDevSalDTO = this.selectOne(jmDevSalEntityQueryWrapper);
            //判断 usrcode 是否重复
            if (jmDevSalDTO==null || jmDevSalDTO.getDevNo()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn saveDevSals(List<JmDevSalDTO> dtos) {
        CommonReturn result = new CommonReturn();

        try{
            for (JmDevSalDTO dto : dtos){
                dto.setHpdate(new Date());
            }
            jmDevSalDao.saveJmDevSals(dtos);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }

        return result;
    }

    @DS("master")
    @Override
    public CommonReturn editDevSal(JmDevSalDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && dto.getDevNo()!=null){
            //获取原先的用户属性值
            QueryWrapper<JmDevSalEntity> jmDevSalEntityQueryWrapper = new QueryWrapper<>();
            jmDevSalEntityQueryWrapper.eq("dev_no",dto.getDevNo());
            JmDevSalDTO jmDevSalDTO = this.selectOne(jmDevSalEntityQueryWrapper);
            //设置用户不能操作的属性
            try{
//                this.edit(dto);
                jmDevSalDao.editJmDevSal(dto);
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
    public CommonReturn delDevSal(List<String> devNos,List<String> salNos) {
        CommonReturn result = new CommonReturn();
        try{
            if(devNos==null || salNos==null || devNos.size()==0 || salNos.size()==0 || devNos.size()==salNos.size()){
                for (int i = 0;i < devNos.size(); i++){
                    QueryWrapper<JmDevSalEntity> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("dev_no",devNos.get(i));
                    queryWrapper.eq("sal_no",salNos.get(i));
                    this.remove(queryWrapper);
                }
            }
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn getDevSalPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<JmDevSalDTO> jmDevSalDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),getQueryWrapper(dto));
        if (jmDevSalDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmDevSalDTOS,"查找成功");
        }
        return result;
    }

    /**
     * 筛选条件
     * @param dto
     * @return
     */
    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();
        if(dto.getAscOrder()==null && dto.getDescOrder()==null){
            dto.setDescOrder("hpdate");
        }
        if (MyUtils.StringIsNull(dto.getDevNo())){
            queryWrapper.eq("dev_no",dto.getDevNo());
        }
        if (dto.getCid()!=null){
            queryWrapper.eq("cid",dto.getCid());
        }
        if (MyUtils.StringIsNull(dto.getWkNo())){
            queryWrapper.eq("sal_no",dto.getWkNo());
        }
        if (MyUtils.StringIsNull(dto.getSorg())){
            queryWrapper.eq("dep",dto.getSorg());
        }
        if (MyUtils.StringIsNull(dto.getDep())){
            queryWrapper.eq("dep_name",dto.getDep());
        }
        if (MyUtils.StringIsNull(dto.getDevName())){
            queryWrapper.like("dev_name",dto.getDevName());
        }
        if (MyUtils.StringIsNull(dto.getWkName())){
            queryWrapper.like("sal_name",dto.getWkName());
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
        if (dto.getDescOrder()!=null && dto.getAscOrder()==null){
            queryWrapper.orderByDesc(MyUtils.humpToLine((String) dto.getDescOrder()));
        }
        return queryWrapper;
    }

}
