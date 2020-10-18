package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmChkstdMfDao;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.JmChkstdMfDTO;
import com.BSMES.jd.main.entity.InsorgEntity;
import com.BSMES.jd.main.entity.JmChkstdMfEntity;
import com.BSMES.jd.main.service.JmChkstdMfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JmChkstdMfServiceImpl extends BaseServiceImpl<JmChkstdMfDao , JmChkstdMfEntity , JmChkstdMfDTO> implements JmChkstdMfService {



    @Override
    public void beforeInsert(JmChkstdMfDTO dto) {

    }

    @Override
    public void beforEedit(JmChkstdMfDTO dto) {

    }

    @Override
    public CommonReturn getChkstdMf(JmChkstdMfDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto,true);
        List<JmChkstdMfDTO> dtos = this.select(data);
        if(dtos.isEmpty()){
            result.setAll(20000,dtos,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,dtos,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveChkstdMf(JmChkstdMfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的usrcode是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getChkstdNo())){
            QueryWrapper<JmChkstdMfEntity> jmChkstdMfEntityQueryWrapper = new QueryWrapper<>();
            jmChkstdMfEntityQueryWrapper.eq("chkstd_no",dto.getChkstdNo());
            JmChkstdMfDTO chkstdMfDTO = this.selectOne(jmChkstdMfEntityQueryWrapper);
            //判断 usrcode 是否重复
            if (chkstdMfDTO==null || chkstdMfDTO.getChkstdNo()==null){
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
    public CommonReturn editChkstdMf(JmChkstdMfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getChkstdNo())){
            //获取原先的用户属性值
            QueryWrapper<JmChkstdMfEntity> jmChkstdMfEntityQueryWrapper = new QueryWrapper<>();
            jmChkstdMfEntityQueryWrapper.eq("chkstd_no",dto.getChkstdNo());
            JmChkstdMfDTO chkstdMfDTO = this.selectOne(jmChkstdMfEntityQueryWrapper);
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
    public CommonReturn delChkstdMf(List<String> chkstdNos) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmChkstdMfEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("chkstd_no",chkstdNos);
        try{
            this.remove(queryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getChkstdMfPage(JmChkstdMfDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        List<JmChkstdMfDTO> jmChkstdMfDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmChkstdMfDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmChkstdMfDTOS,"查找成功");
        }
        return result;
    }

//    @Override
//    public InsorgDTO getTest(String id) {
//        return null;
//    }


}
