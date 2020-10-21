package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmChkstdMfDao;
import com.BSMES.jd.main.dao.JmChkstdTfDao;
import com.BSMES.jd.main.dto.JmChkstdMfDTO;
import com.BSMES.jd.main.dto.JmChkstdTfDTO;
import com.BSMES.jd.main.entity.JmChkstdMfEntity;
import com.BSMES.jd.main.entity.JmChkstdTfEntity;
import com.BSMES.jd.main.service.JmChkstdMfService;
import com.BSMES.jd.main.service.JmChkstdTfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JmChkstdTfServiceImpl extends BaseServiceImpl<JmChkstdTfDao, JmChkstdTfEntity, JmChkstdTfDTO> implements JmChkstdTfService {

    @Autowired
    JmChkstdTfService jmChkstdTfService;

    @Autowired
    JmChkstdTfDao jmChkstdTfDao;

    @Override
    public void beforeInsert(JmChkstdTfDTO dto) {

    }

    @Override
    public void beforEedit(JmChkstdTfDTO dto) {

    }

    @Override
    public CommonReturn getChkstdTf(JmChkstdTfDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto,true);
        List<JmChkstdTfDTO> dtos = this.select(data);
        if(dtos.isEmpty()){
            result.setAll(20000,dtos,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,dtos,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveChkstdTf(JmChkstdTfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的usrcode是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getChkstdNo())){
            QueryWrapper<JmChkstdTfEntity> jmChkstdTfEntityQueryWrapper = new QueryWrapper<>();
            jmChkstdTfEntityQueryWrapper.eq("chkstd_no",dto.getChkstdNo());
            JmChkstdTfDTO chkstdTfDTO = this.selectOne(jmChkstdTfEntityQueryWrapper);
            //判断 usrcode 是否重复
            if (chkstdTfDTO==null || chkstdTfDTO.getChkstdNo()==null){
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
    public CommonReturn saveChkstdTfs(List<JmChkstdTfDTO> dtos) {
        CommonReturn result = new CommonReturn();
        try{
            jmChkstdTfDao.insertJmChkstdTfs(dtos);;
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CommonReturn editChkstdTf(JmChkstdTfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getChkstdNo())){
            //获取原先的用户属性值
            QueryWrapper<JmChkstdTfEntity> jmChkstdTfEntityQueryWrapper = new QueryWrapper<>();
            jmChkstdTfEntityQueryWrapper.eq("chkstd_no",dto.getChkstdNo());
            JmChkstdTfDTO chkstdTfDTO = this.selectOne(jmChkstdTfEntityQueryWrapper);
            //设置用户不能操作的属性
            try{
//                this.edit(dto);
                jmChkstdTfDao.editJmChkstdTfs(dto);
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
    public CommonReturn delChkstdTf(List<String> chkstdNos,List<Integer> cids) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmChkstdTfEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("chkstd_no",chkstdNos);
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
    public CommonReturn getChkstdTfPage(JmChkstdTfDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        IPage<JmChkstdTfDTO> jmChkstdTfDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmChkstdTfDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmChkstdTfDTOS,"查找成功");
        }
        return result;
    }



}
