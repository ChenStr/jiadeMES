package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmGoodsCheckTfDao;
import com.BSMES.jd.main.dto.JmCheckPlanTfDTO;
import com.BSMES.jd.main.dto.JmGoodsCheckTfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmCheckPlanTfEntity;
import com.BSMES.jd.main.entity.JmGoodsCheckTfEntity;
import com.BSMES.jd.main.service.JmGoodsCheckTfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JmGoodsCheckTfServiceImpl extends BaseServiceImpl<JmGoodsCheckTfDao, JmGoodsCheckTfEntity, JmGoodsCheckTfDTO> implements JmGoodsCheckTfService {

    @Autowired
    JmGoodsCheckTfDao jmGoodsCheckTfDao;

    @Override
    public void beforeInsert(JmGoodsCheckTfDTO dto) {
        if (dto.getCreateTime()==null){
            dto.setCreateTime(new Date());
        }
    }

    @Override
    public void beforEedit(JmGoodsCheckTfDTO dto) {

    }

    @Override
    public CommonReturn getGoods(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmGoodsCheckTfDTO> jmGoodsCheckTfDTOS = this.select(this.getQueryWrapper(dto));
        if(jmGoodsCheckTfDTOS.isEmpty()){
            result.setAll(20000,jmGoodsCheckTfDTOS,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,jmGoodsCheckTfDTOS,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveGoods(List<JmGoodsCheckTfDTO> dtos) {
        CommonReturn result = new CommonReturn();
        try{
            jmGoodsCheckTfDao.saveGoodTfS(dtos);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(20000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CommonReturn editGoods(JmGoodsCheckTfDTO dto) {
        CommonReturn result = new CommonReturn();
        try{
            jmGoodsCheckTfDao.editGoodTfS(dto);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(20000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CommonReturn delGoods(List<String> sids, List<Integer> cids) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmGoodsCheckTfEntity> queryWrapper = new QueryWrapper<>();
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
    public CommonReturn getGoodsPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<JmGoodsCheckTfDTO> jmGoodsCheckTfDTOIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        if (jmGoodsCheckTfDTOIPage==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmGoodsCheckTfDTOIPage,"查找成功");
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
        if (MyUtils.StringIsNull(dto.getDep())){
            queryWrapper.eq("item_id",dto.getDep());
        }
        if (MyUtils.StringIsNull(dto.getSorg())){
            queryWrapper.eq("item_name",dto.getSorg());
        }
        if (MyUtils.StringIsNull(dto.getType())){
            queryWrapper.eq("skill",dto.getType());
        }
        if (MyUtils.StringIsNull(dto.getMouldNo())){
            queryWrapper.eq("testing_data",dto.getMouldNo());
        }
        if (MyUtils.StringIsNull(dto.getMouldName())){
            queryWrapper.eq("test_to_decide",dto.getMouldName());
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
