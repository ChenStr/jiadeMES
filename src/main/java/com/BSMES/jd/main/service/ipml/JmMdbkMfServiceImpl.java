package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmMdbkMfDao;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.JmMdbkMfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.InsorgEntity;
import com.BSMES.jd.main.entity.JmMdbkMfEntity;
import com.BSMES.jd.main.service.JmMdbkMfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JmMdbkMfServiceImpl extends BaseServiceImpl<JmMdbkMfDao, JmMdbkMfEntity, JmMdbkMfDTO> implements JmMdbkMfService {

    @Autowired
    InssysvarServiceImpl inssysvarService;

    @Override
    public void beforeInsert(JmMdbkMfDTO dto) {

    }

    @Override
    public void beforEedit(JmMdbkMfDTO dto) {

    }

    @Override
    public CommonReturn getMdbkMf(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<InsorgDTO> sorgs = this.select(this.getQueryWrapper(dto));
        if(sorgs.isEmpty()){
            result.setAll(20000,sorgs,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,sorgs,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveMdbkMf(JmMdbkMfDTO dto) {
        CommonReturn result = new CommonReturn();
        if (dto.getSid()==null){
            dto.setSid(getKey("Mdbk","sid",inssysvarService,dto));
        }
        //判断dto是否为空 判断dto的usrcode是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            QueryWrapper<JmMdbkMfEntity> sorgQueryWrapper = new QueryWrapper<>();
            sorgQueryWrapper.eq("sid",dto.getSid());
            JmMdbkMfDTO jmMdbkMfDTO = this.selectOne(sorgQueryWrapper);
            //判断 usrcode 是否重复
            if (jmMdbkMfDTO==null || jmMdbkMfDTO.getSid()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"模具还回单号已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn editMdbkMf(JmMdbkMfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            //获取原先的用户属性值
            QueryWrapper<JmMdbkMfEntity> jmMdbkMfEntityQueryWrapper = new QueryWrapper<>();
            jmMdbkMfEntityQueryWrapper.eq("sid",dto.getSid());
            JmMdbkMfDTO var = this.selectOne(jmMdbkMfEntityQueryWrapper);
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
    public CommonReturn delMdbkMf(List<String> sids) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmMdbkMfEntity> queryWrapper = new QueryWrapper<>();
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
    public CommonReturn getMdbkMfPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<JmMdbkMfDTO> jmMdbkMfDTOIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        if (jmMdbkMfDTOIPage==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmMdbkMfDTOIPage,"查找成功");
        }
        return result;
    }

    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();

//        if(dto.getAscOrder()==null && dto.getDescOrder()==null){
//            dto.setDescOrder("sort");
//        }
//
//        if (MyUtils.StringIsNull(dto.getSid())){
//            queryWrapper.like("orgcode",dto.getSid());
//        }
//        if (MyUtils.StringIsNull(dto.getDevName())){
//            queryWrapper.like("orgname",dto.getDevName());
//        }
//        if (MyUtils.StringIsNull(dto.getType())){
//            queryWrapper.eq("cattr",dto.getType());
//        }
//
//        if (dto.getAscOrder()!=null){
//            queryWrapper.orderByAsc(MyUtils.humpToLine((String) dto.getAscOrder()));
//        }
//        if (dto.getDescOrder()!=null && dto.getAscOrder()==null){
//            queryWrapper.orderByDesc(MyUtils.humpToLine((String) dto.getDescOrder()));
//        }


        return queryWrapper;
    }
}
