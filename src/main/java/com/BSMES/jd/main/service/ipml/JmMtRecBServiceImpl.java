package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmMtRecBDao;
import com.BSMES.jd.main.dto.JmDevSalDTO;
import com.BSMES.jd.main.dto.JmMtRecBDTO;
import com.BSMES.jd.main.dto.JmMtRecDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmMtRecBEntity;
import com.BSMES.jd.main.entity.JmMtRecEntity;
import com.BSMES.jd.main.service.JmMtRecBService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JmMtRecBServiceImpl extends BaseServiceImpl<JmMtRecBDao, JmMtRecBEntity, JmMtRecBDTO> implements JmMtRecBService {

    @Autowired
    JmMtRecBDao jmMtRecBDao;

    @Override
    public void beforeInsert(JmMtRecBDTO dto) {

    }

    @Override
    public void beforEedit(JmMtRecBDTO dto) {

    }

    @DS("master")
    @Override
    public CommonReturn getMtRecB(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmMtRecDTO> mts = this.select(this.getQueryWrapper(dto));
        if(mts.isEmpty()){
            result.setAll(20000,mts,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,mts,"查找成功");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn saveMtRecBs(List<JmMtRecBDTO> dtos) {
        CommonReturn result = new CommonReturn();
        try{
            jmMtRecBDao.saveMtRecBs(dtos);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return null;
    }

    @DS("master")
    @Override
    public CommonReturn saveMtRecB(JmMtRecBDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getWxNo())){
            QueryWrapper<JmMtRecBEntity> mtRecQueryWrapper = new QueryWrapper<>();
            mtRecQueryWrapper.eq("wx_no",dto.getWxNo());
            JmMtRecBDTO mt = this.selectOne(mtRecQueryWrapper);
            //判断 usrcode 是否重复
            if (mt==null || mt.getWxNo()==null){
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

    @DS("master")
    @Override
    public CommonReturn editMtRecB(JmMtRecBDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getWxNo())){
            //获取原先的人员属性值
            QueryWrapper<JmMtRecBEntity> mtRecQueryWrapper = new QueryWrapper<>();
            mtRecQueryWrapper.eq("wx_no",dto.getWxNo());
            JmMtRecBDTO mt = this.selectOne(mtRecQueryWrapper);
            //设置用户不能操作的属性
            try{
//                this.edit(dto);
                jmMtRecBDao.editMtRecB(dto);
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
    public CommonReturn delMtRecB(List<String> wxNos, List<Integer> cids) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmMtRecBEntity> mtRecQueryWrapper = new QueryWrapper<>();
        mtRecQueryWrapper.in("wx_no",wxNos);
        mtRecQueryWrapper.in("cids",cids);
        try{
            this.remove(mtRecQueryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn getMtRecBPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<JmMtRecBDTO> jmMtRecBDTOIPage = this.selectPage(dto.getPage(),dto.getPageSize(),getQueryWrapper(dto));
        if (jmMtRecBDTOIPage==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmMtRecBDTOIPage,"查找成功");
        }
        return result;
    }

    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();

        if(dto.getAscOrder()==null && dto.getDescOrder()==null){
            dto.setDescOrder("hpdate");
        }

        if (MyUtils.StringIsNull(dto.getWkNo())){
            queryWrapper.eq("wx_no",dto.getWkNo());
        }
        if (dto.getCid()!=null){
            queryWrapper.eq("cid",dto.getCid());
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
