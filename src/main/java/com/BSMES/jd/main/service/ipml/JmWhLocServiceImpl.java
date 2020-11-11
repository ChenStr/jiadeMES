package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmWhLocDao;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.JmWhLocDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.InsorgEntity;
import com.BSMES.jd.main.entity.JmWhLocEntity;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.main.service.JmWhLocService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JmWhLocServiceImpl extends BaseServiceImpl<JmWhLocDao , JmWhLocEntity , JmWhLocDTO> implements JmWhLocService {

    @Autowired
    InssysvarService inssysvarService;


    @Override
    public void beforeInsert(JmWhLocDTO dto) {

    }

    @Override
    public void beforEedit(JmWhLocDTO dto) {

    }

    @DS("master")
    @Override
    public CommonReturn getWhLoc(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmWhLocDTO> jmWhLocDTOS = this.select(this.getQueryWrapper(dto));
        if(jmWhLocDTOS.isEmpty()){
            result.setAll(20000,jmWhLocDTOS,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,jmWhLocDTOS,"查找成功");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn saveWhLoc(JmWhLocDTO dto) {
        CommonReturn result = new CommonReturn();
        if (dto.getWhLoc()==null){
            dto.setWhLoc(getKey("WhLoc","wh_loc",inssysvarService,dto));
        }
        //判断dto是否为空 判断dto的usrcode是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getWhLoc())){
            QueryWrapper<JmWhLocEntity> jmWhLocQueryWrapper = new QueryWrapper<>();
            jmWhLocQueryWrapper.eq("wh_loc",dto.getWhLoc());
            JmWhLocDTO whLocDTO = this.selectOne(jmWhLocQueryWrapper);
            //判断 usrcode 是否重复
            if (whLocDTO==null || whLocDTO.getWhLoc()==null){
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
    public CommonReturn editWhLoc(JmWhLocDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getWhLoc())){
            //获取原先的用户属性值
            QueryWrapper<JmWhLocEntity> jmWhLocQueryWrapper = new QueryWrapper<>();
            jmWhLocQueryWrapper.eq("wh_loc",dto.getWhLoc());
            JmWhLocDTO var = this.selectOne(jmWhLocQueryWrapper);
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
    public CommonReturn delWhLoc(List<String> whLocs) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmWhLocEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("wh_loc",whLocs);
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
    public CommonReturn getWhLocPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<JmWhLocDTO> jmWhLocDTOIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        if (jmWhLocDTOIPage==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmWhLocDTOIPage,"查找成功");
        }
        return result;
    }

    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();

        if (MyUtils.StringIsNull(dto.getSid())){
            queryWrapper.eq("wh_loc",dto.getSid());
        }
        if (MyUtils.StringIsNull(dto.getOtherId())){
            queryWrapper.like("name",dto.getOtherId());
        }
        if (MyUtils.StringIsNull(dto.getType())){
            queryWrapper.like("wh",dto.getType());
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
