package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmDevDao;
import com.BSMES.jd.main.dto.JmDevDTO;
import com.BSMES.jd.main.entity.JmDevEntity;
import com.BSMES.jd.main.service.JmDevService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JmDevServiceImpl extends BaseServiceImpl<JmDevDao, JmDevEntity, JmDevDTO> implements JmDevService {
    @Override
    public void beforeInsert(JmDevDTO dto) {

    }

    @Override
    public void beforEedit(JmDevDTO dto) {

    }


    @Override
    public CommonReturn getDev(JmDevDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto);
        List<JmDevDTO> devs = this.select(data);
        if(devs.isEmpty()){
            result.setAll(20000,devs,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,devs,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveDev(JmDevDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getDevNo())){
            QueryWrapper<JmDevEntity> devQueryWrapper = new QueryWrapper<>();
            devQueryWrapper.eq("dev_no",dto.getDevNo());
            JmDevDTO dev = this.selectOne(devQueryWrapper);
            //判断 usrcode 是否重复
            if (dev==null || dev.getDevNo()==null){
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
    public CommonReturn editDev(JmDevDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getDevNo())){
            //获取原先的人员属性值
            QueryWrapper<JmDevEntity> devQueryWrapper = new QueryWrapper<>();
            devQueryWrapper.eq("dev_no",dto.getDevNo());
            JmDevDTO worker = this.selectOne(devQueryWrapper);
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
    public CommonReturn delDev(List<String> devNos) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmDevEntity> devQueryWrapper = new QueryWrapper<>();
        devQueryWrapper.in("dev_no",devNos);
        try{
            this.remove(devQueryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getDevPage(JmDevDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        List<JmDevDTO> jmDevDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmDevDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmDevDTOS,"查找成功");
        }
        return result;
    }
}
