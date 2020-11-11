package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmPrdtutDao;
import com.BSMES.jd.main.dto.JmPrdtDTO;
import com.BSMES.jd.main.dto.JmPrdtutDTO;
import com.BSMES.jd.main.entity.JmPrdtEntity;
import com.BSMES.jd.main.entity.JmPrdtutEntity;
import com.BSMES.jd.main.service.JmPrdtutService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class JmPrdtutServiceImpl extends BaseServiceImpl<JmPrdtutDao , JmPrdtutEntity , JmPrdtutDTO> implements JmPrdtutService {
    @Override
    public void beforeInsert(JmPrdtutDTO dto) {

    }

    @Override
    public void beforEedit(JmPrdtutDTO dto) {

    }

    @DS("master")
    @Override
    public CommonReturn getPrdtut(JmPrdtutDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto,true);
        List<JmPrdtutDTO> prdts = this.select(data);
        if(prdts.isEmpty()){
            result.setAll(20000,prdts,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,prdts,"查找成功");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn savePrdtut(JmPrdtutDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getUbm())){
            QueryWrapper<JmPrdtutEntity> prdtutQueryWrapper = new QueryWrapper<>();
            prdtutQueryWrapper.eq("ubm",dto.getUbm());
            JmPrdtutDTO prdt = this.selectOne(prdtutQueryWrapper);
            //判断 ubm 是否重复
            if (prdt==null || prdt.getUbm()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"货品号已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn editPrdtut(JmPrdtutDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getUbm())){
            //获取原先的人员属性值
            QueryWrapper<JmPrdtutEntity> prdtutQueryWrapper = new QueryWrapper<>();
            prdtutQueryWrapper.eq("ubm",dto.getUbm());
            JmPrdtutDTO worker = this.selectOne(prdtutQueryWrapper);
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
    public CommonReturn delPrdtut(List<String> ubms) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmPrdtutEntity> prdtutQueryWrapper = new QueryWrapper<>();
        prdtutQueryWrapper.in("ubm",ubms);
        try{
            this.remove(prdtutQueryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn getPrdtutPage(JmPrdtutDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        IPage<JmPrdtDTO> jmPrdtDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmPrdtDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmPrdtDTOS,"查找成功");
        }
        return result;
    }
}
