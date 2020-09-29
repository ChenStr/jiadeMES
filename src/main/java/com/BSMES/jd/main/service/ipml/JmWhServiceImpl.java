package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmWhDao;
import com.BSMES.jd.main.dto.JmWhDTO;
import com.BSMES.jd.main.entity.JmWhEntity;
import com.BSMES.jd.main.service.JmWhService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JmWhServiceImpl extends BaseServiceImpl<JmWhDao , JmWhEntity , JmWhDTO> implements JmWhService {
    @Override
    public void beforeInsert(JmWhDTO dto) {

    }

    @Override
    public void beforEedit(JmWhDTO dto) {

    }

    @Override
    public CommonReturn getWh(JmWhDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto);
        List<JmWhDTO> whs = this.select(data);
        if(whs.isEmpty()){
            result.setAll(20000,whs,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,whs,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveWh(JmWhDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getWh())){
            QueryWrapper<JmWhEntity> whQueryWrapper = new QueryWrapper<>();
            whQueryWrapper.eq("wh",dto.getWh());
            JmWhDTO worker = this.selectOne(whQueryWrapper);
            //判断 usrcode 是否重复
            if (worker==null || worker.getWh()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"仓库号已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn editWh(JmWhDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getWh())){
            //获取原先的人员属性值
            QueryWrapper<JmWhEntity> whQueryWrapper = new QueryWrapper<>();
            whQueryWrapper.eq("wh",dto.getWh());
            JmWhDTO wh = this.selectOne(whQueryWrapper);
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
    public CommonReturn delWh(List<String> whs) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmWhEntity> whQueryWrapper = new QueryWrapper<>();
        whQueryWrapper.in("wh",whs);
        try{
            this.remove(whQueryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getWhPage(JmWhDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        List<JmWhDTO> jmWhDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmWhDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmWhDTOS,"查找成功");
        }
        return result;
    }
}
