package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.InsorgDao;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.InsorgEntity;
import com.BSMES.jd.main.service.InsorgService;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InsorgServiceImpl extends BaseServiceImpl<InsorgDao , InsorgEntity , InsorgDTO> implements InsorgService {

    @Autowired
    InssysvarService inssysvarService;

    @Override
    public void beforeInsert(InsorgDTO dto) {
        //首先先将cCorp默认值加上
        dto.setCCorp(0);
    }

    @Override
    public void beforEedit(InsorgDTO dto) {

    }

    @DS("master")
    @Override
    public CommonReturn getSorg(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<InsorgDTO> sorgs = this.select(this.getQueryWrapper(dto));
        if(sorgs.isEmpty()){
            result.setAll(20000,sorgs,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,sorgs,"查找成功");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn saveSorg(InsorgDTO dto) {
        CommonReturn result = new CommonReturn();
        if (dto.getOrgcode()==null){
            dto.setOrgcode(getKey("Sorg","orgcode",inssysvarService,dto));
        }
        //判断dto是否为空 判断dto的usrcode是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getOrgcode())){
            QueryWrapper<InsorgEntity> sorgQueryWrapper = new QueryWrapper<>();
            sorgQueryWrapper.eq("orgcode",dto.getOrgcode());
            InsorgDTO sorg = this.selectOne(sorgQueryWrapper);
            //判断 usrcode 是否重复
            if (sorg==null || sorg.getOrgcode()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"部门号已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn editSorg(InsorgDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getOrgcode())){
            //获取原先的用户属性值
            QueryWrapper<InsorgEntity> sorgqueryWrapper = new QueryWrapper<>();
            sorgqueryWrapper.eq("orgcode",dto.getOrgcode());
            InsorgDTO var = this.selectOne(sorgqueryWrapper);
            //设置用户不能操作的属性
            dto.setCCorp(var.getCCorp());
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
    public CommonReturn delSorg(List<String> orgcodes) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<InsorgEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("orgcode",orgcodes);
        try{
            this.remove(queryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn getSorgPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<InsorgDTO> insorgDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        if (insorgDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,insorgDTOS,"查找成功");
        }
        return result;
    }

    @DS("master")
    @Override
    public InsorgDTO getTest(String id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("orgcode",id);
        InsorgDTO insorgDTO =  selectOne(queryWrapper);
        return insorgDTO;
    }

    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();

        if(dto.getAscOrder()==null && dto.getDescOrder()==null){
            dto.setDescOrder("sort");
        }

        if (MyUtils.StringIsNull(dto.getSid())){
            queryWrapper.like("orgcode",dto.getSid());
        }
        if (MyUtils.StringIsNull(dto.getDevName())){
            queryWrapper.like("orgname",dto.getDevName());
        }
        if (MyUtils.StringIsNull(dto.getType())){
            queryWrapper.eq("cattr",dto.getType());
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
