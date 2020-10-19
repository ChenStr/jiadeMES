package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmDevSalDao;
import com.BSMES.jd.main.dto.JmChkstdTfDTO;
import com.BSMES.jd.main.dto.JmDevSalDTO;
import com.BSMES.jd.main.entity.JmChkstdTfEntity;
import com.BSMES.jd.main.entity.JmDevSalEntity;
import com.BSMES.jd.main.service.JmDevSalService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JmDevSalServiceImpl extends BaseServiceImpl<JmDevSalDao , JmDevSalEntity , JmDevSalDTO> implements JmDevSalService {
    @Override
    public void beforeInsert(JmDevSalDTO dto) {

    }

    @Override
    public void beforEedit(JmDevSalDTO dto) {

    }

    @Override
    public CommonReturn getDevSal(JmDevSalDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto,true);
        List<JmDevSalDTO> dtos = this.select(data);
        if(dtos.isEmpty()){
            result.setAll(20000,dtos,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,dtos,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveDevSal(JmDevSalDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的usrcode是否有值
        if (dto!=null && dto.getCid()!=null){
            QueryWrapper<JmDevSalEntity> jmDevSalEntityQueryWrapper = new QueryWrapper<>();
            jmDevSalEntityQueryWrapper.eq("cid",dto.getCid());
            JmDevSalDTO jmDevSalDTO = this.selectOne(jmDevSalEntityQueryWrapper);
            //判断 usrcode 是否重复
            if (jmDevSalDTO==null || jmDevSalDTO.getCid()==null){
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

    @Override
    public CommonReturn editDevSal(JmDevSalDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && dto.getCid()!=null){
            //获取原先的用户属性值
            QueryWrapper<JmDevSalEntity> jmDevSalEntityQueryWrapper = new QueryWrapper<>();
            jmDevSalEntityQueryWrapper.eq("cid",dto.getCid());
            JmDevSalDTO jmDevSalDTO = this.selectOne(jmDevSalEntityQueryWrapper);
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
    public CommonReturn delDevSal(List<Integer> cids) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmDevSalEntity> queryWrapper = new QueryWrapper<>();
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
    public CommonReturn getDevSalPage(JmDevSalDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        List<JmDevSalDTO> jmDevSalDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmDevSalDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmDevSalDTOS,"查找成功");
        }
        return result;
    }
}
