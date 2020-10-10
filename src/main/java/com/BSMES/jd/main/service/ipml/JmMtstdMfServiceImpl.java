package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmMtstdMfDao;
import com.BSMES.jd.main.dto.JmDevDTO;
import com.BSMES.jd.main.dto.JmMtIdDTO;
import com.BSMES.jd.main.dto.JmMtstdMfDTO;
import com.BSMES.jd.main.entity.JmDevEntity;
import com.BSMES.jd.main.entity.JmMtIdEntity;
import com.BSMES.jd.main.entity.JmMtstdMfEntity;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.main.service.JmDevService;
import com.BSMES.jd.main.service.JmMtstdMfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JmMtstdMfServiceImpl extends BaseServiceImpl<JmMtstdMfDao , JmMtstdMfEntity , JmMtstdMfDTO> implements JmMtstdMfService {

    @Autowired
    InssysvarService inssysvarService;

    @Autowired
    JmDevService jmDevService;

    @Override
    public void beforeInsert(JmMtstdMfDTO dto) {

    }

    @Override
    public void beforEedit(JmMtstdMfDTO dto) {

    }

    @Override
    public CommonReturn getMtstdMf(JmMtstdMfDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto);
        List<JmMtstdMfDTO> mtstdMf = this.select(data);
        if(mtstdMf.isEmpty()){
            result.setAll(20000,mtstdMf,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,mtstdMf,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn getMtstdMfPlus(JmMtstdMfDTO dto) {
        return null;
    }

    @Override
    public CommonReturn saveMtstdMf(JmMtstdMfDTO dto) {
        CommonReturn result = new CommonReturn();
        dto.setMtstdNo(this.getKey("JmMtstdMf" , "mtstd_no" , inssysvarService , dto));
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getMtstdNo())){
            QueryWrapper<JmMtstdMfEntity> mtstdMfQueryWrapper = new QueryWrapper<>();
            mtstdMfQueryWrapper.eq("mtstd_no",dto.getMtstdNo());
            JmMtstdMfDTO mt = this.selectOne(mtstdMfQueryWrapper);
            //判断设备是否存在
            QueryWrapper<JmDevEntity> devQueryWrapper = new QueryWrapper<>();
            devQueryWrapper.eq("dev_no",dto.getDevNo());
            JmDevDTO devDTO = jmDevService.selectOne(devQueryWrapper);
            //判断 usrcode 是否重复
            if ( (devDTO!=null && devDTO.getDevNo()!=null) && (mt==null || mt.getMtstdNo()==null)){
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
    public CommonReturn editMtstdMf(JmMtstdMfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getMtstdNo())){
            //获取原先的人员属性值
            QueryWrapper<JmMtstdMfEntity> mtstdMfQueryWrapper = new QueryWrapper<>();
            mtstdMfQueryWrapper.eq("mtstd_no",dto.getMtstdNo());
            JmMtstdMfDTO mt = this.selectOne(mtstdMfQueryWrapper);
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
    public CommonReturn delMtstdMf(List<String> mtstdNos) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmMtstdMfEntity> mtstdMfQueryWrapper = new QueryWrapper<>();
        mtstdMfQueryWrapper.in("mtstd_no",mtstdNos);
        try{
            this.remove(mtstdMfQueryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getMtstdMfPage(JmMtstdMfDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        List<JmMtstdMfDTO> jmMtstdMfDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmMtstdMfDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmMtstdMfDTOS,"查找成功");
        }
        return result;
    }
}
