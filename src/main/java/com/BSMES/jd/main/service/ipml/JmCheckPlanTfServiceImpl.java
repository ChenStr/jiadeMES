package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmCheckPlanTfDao;
import com.BSMES.jd.main.dto.JmCheckPlanTfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmCheckPlanTfEntity;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.main.service.JmCheckPlanTfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JmCheckPlanTfServiceImpl extends BaseServiceImpl<JmCheckPlanTfDao, JmCheckPlanTfEntity, JmCheckPlanTfDTO> implements JmCheckPlanTfService {

    @Autowired
    InssysvarService inssysvarService;

    @Autowired
    JmCheckPlanTfDao jmCheckPlanTfDao;

    @Override
    public void beforeInsert(JmCheckPlanTfDTO dto) {
        if (dto.getCreateTime()==null){
            dto.setCreateTime(new Date());
        }
    }

    @Override
    public void beforEedit(JmCheckPlanTfDTO dto) {

    }

    @Override
    public CommonReturn getPlanTf(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmCheckPlanTfDTO> jmCheckPlanTfDTOS = this.select(this.getQueryWrapper(dto));
        if(jmCheckPlanTfDTOS.isEmpty()){
            result.setAll(20000,jmCheckPlanTfDTOS,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,jmCheckPlanTfDTOS,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn savePlanTf(List<JmCheckPlanTfDTO> dtos) {
        CommonReturn result = new CommonReturn();
        try{
            jmCheckPlanTfDao.savePlanTfS(dtos);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(20000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CommonReturn editPlanTf(JmCheckPlanTfDTO dto) {
        CommonReturn result = new CommonReturn();
        try{
            jmCheckPlanTfDao.updatePlanTf(dto);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(20000,null,"操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn delPlanTf(List<String> sids, List<Integer> cids) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmCheckPlanTfEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("sid",sids);
        queryWrapper.in("cid",cids);
        try{
            this.remove(queryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getPlanTfPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<JmCheckPlanTfDTO> jmCheckPlanTfDTOIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        if (jmCheckPlanTfDTOIPage==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmCheckPlanTfDTOIPage,"查找成功");
        }
        return result;
    }

    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();

//        if(dto.getAscOrder()==null && dto.getDescOrder()==null){
//            dto.setDescOrder("sort");
//        }

        if (MyUtils.StringIsNull(dto.getSid())){
            queryWrapper.eq("sid",dto.getSid());
        }
        if (MyUtils.StringIsNull(dto.getOtherId())){
            queryWrapper.eq("name",dto.getOtherId());
        }
        if (MyUtils.StringIsNull(dto.getMoNo())){
            queryWrapper.eq("skill",dto.getMoNo());
        }
        if (MyUtils.StringIsNull(dto.getType())){
            queryWrapper.eq("item_name",dto.getType());
        }
        if (MyUtils.StringIsNull(dto.getOtherType())){
            queryWrapper.eq("mode",dto.getOtherType());
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
