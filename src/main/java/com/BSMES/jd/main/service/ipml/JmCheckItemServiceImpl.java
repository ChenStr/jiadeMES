package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmCheckItemDao;
import com.BSMES.jd.main.dto.JmCheckItemDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmCheckItemEntity;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.main.service.JmCheckItemService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JmCheckItemServiceImpl extends BaseServiceImpl<JmCheckItemDao, JmCheckItemEntity, JmCheckItemDTO> implements JmCheckItemService {

    @Autowired
    InssysvarService inssysvarService;

    @Override
    public void beforeInsert(JmCheckItemDTO dto) {
        if (dto.getCreateTime()==null){
            dto.setCreateTime(new Date());
        }
    }

    @Override
    public void beforEedit(JmCheckItemDTO dto) {

    }

    @Override
    public CommonReturn getCheck(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmCheckItemDTO> jmCheckItemDTOS = this.select(this.getQueryWrapper(dto));
        if(jmCheckItemDTOS.isEmpty()){
            result.setAll(20000,jmCheckItemDTOS,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,jmCheckItemDTOS,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveCheck(JmCheckItemDTO dto) {
        CommonReturn result = new CommonReturn();
        if (dto.getSid()==null){
            dto.setSid(getKey("User","sid",inssysvarService,dto));
        }
        //判断dto是否为空 判断dto的usrcode是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            QueryWrapper<JmCheckItemEntity> jmCheckItemEntityQueryWrapper = new QueryWrapper<>();
            jmCheckItemEntityQueryWrapper.eq("sid",dto.getSid());
            JmCheckItemDTO jmCheckItemDTO = this.selectOne(jmCheckItemEntityQueryWrapper);
            //判断 usrcode 是否重复
            if (jmCheckItemDTO==null || jmCheckItemDTO.getSid()==null){
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
    public CommonReturn editCheck(JmCheckItemDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            //获取原先的用户属性值
            QueryWrapper<JmCheckItemEntity> jmCheckItemEntityQueryWrapper = new QueryWrapper<>();
            jmCheckItemEntityQueryWrapper.eq("sid",dto.getSid());
            JmCheckItemDTO var = this.selectOne(jmCheckItemEntityQueryWrapper);
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
    public CommonReturn delCheck(List<String> sids) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmCheckItemEntity> queryWrapper = new QueryWrapper<>();
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
    public CommonReturn getCheckPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<JmCheckItemDTO> jmChkSpcDTOIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        if (jmChkSpcDTOIPage==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmChkSpcDTOIPage,"查找成功");
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
        if (MyUtils.StringIsNull(dto.getType())){
            queryWrapper.eq("check_type",dto.getType());
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
