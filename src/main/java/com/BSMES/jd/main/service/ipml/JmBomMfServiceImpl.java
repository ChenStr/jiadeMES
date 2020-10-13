package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmBomMfDao;
import com.BSMES.jd.main.dto.JmBomMfDTO;
import com.BSMES.jd.main.dto.JmDevDTO;
import com.BSMES.jd.main.entity.JmBomMfEntity;
import com.BSMES.jd.main.entity.JmDevEntity;
import com.BSMES.jd.main.service.JmBomMfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JmBomMfServiceImpl extends BaseServiceImpl<JmBomMfDao , JmBomMfEntity , JmBomMfDTO> implements JmBomMfService {
    @Override
    public void beforeInsert(JmBomMfDTO dto) {

    }

    @Override
    public void beforEedit(JmBomMfDTO dto) {

    }

    @Override
    public CommonReturn getBomMf(JmBomMfDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto,true);
        List<JmBomMfDTO> bomMf = this.select(data);
        if(bomMf.isEmpty()){
            result.setAll(20000,bomMf,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,bomMf,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveBomMf(JmBomMfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getBomNo())){
            QueryWrapper<JmBomMfEntity> bomMfQueryWrapper = new QueryWrapper<>();
            bomMfQueryWrapper.eq("bom_no",dto.getBomNo());
            JmBomMfDTO bomMf = this.selectOne(bomMfQueryWrapper);
            //判断 usrcode 是否重复
            if (bomMf==null || bomMf.getBomNo()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"设备已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn editBomMf(JmBomMfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getBomNo())){
            //获取原先的人员属性值
            QueryWrapper<JmBomMfEntity> bomMfQueryWrapper = new QueryWrapper<>();
            bomMfQueryWrapper.eq("dev_no",dto.getBomNo());
            JmBomMfDTO bomMf = this.selectOne(bomMfQueryWrapper);
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
    public CommonReturn delBomMf(List<String> bomNos) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmBomMfEntity> bomMfQueryWrapper = new QueryWrapper<>();
        bomMfQueryWrapper.in("bom_no",bomNos);
        try{
            this.remove(bomMfQueryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getBomMfPage(JmBomMfDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        List<JmBomMfDTO> jmBomMfDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmBomMfDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmBomMfDTOS,"查找成功");
        }
        return result;
    }
}
