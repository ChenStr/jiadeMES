package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmMoMfDao;
import com.BSMES.jd.main.dto.JmMoMfDTO;
import com.BSMES.jd.main.entity.JmMoMfEntity;
import com.BSMES.jd.main.service.JmMoMfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JmMoMfServiceImpl extends BaseServiceImpl<JmMoMfDao , JmMoMfEntity , JmMoMfDTO > implements JmMoMfService {
    @Override
    public void beforeInsert(JmMoMfDTO dto) {

    }

    @Override
    public void beforEedit(JmMoMfDTO dto) {

    }

    @Override
    public CommonReturn getMoMf(JmMoMfDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto);
        List<JmMoMfDTO> moMfs = this.select(data);
        if(moMfs.isEmpty()){
            result.setAll(20000,moMfs,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,moMfs,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveMoMf(JmMoMfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            QueryWrapper<JmMoMfEntity> moMfQueryWrapper = new QueryWrapper<>();
            moMfQueryWrapper.eq("sid",dto.getSid());
            JmMoMfDTO moMf = this.selectOne(moMfQueryWrapper);
            //判断 usrcode 是否重复
            if (moMf==null || moMf.getSid()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"制令单号已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn editMoMf(JmMoMfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            //获取原先的人员属性值
            QueryWrapper<JmMoMfEntity> moMfQueryWrapper = new QueryWrapper<>();
            moMfQueryWrapper.eq("sid",dto.getSid());
            JmMoMfDTO moMf = this.selectOne(moMfQueryWrapper);
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
    public CommonReturn delMoMf(List<String> sids) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmMoMfEntity> moMfQueryWrapper = new QueryWrapper<>();
        moMfQueryWrapper.in("sid",sids);
        try{
            this.remove(moMfQueryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(20000, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getMoMfPage(JmMoMfDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        List<JmMoMfDTO> jmMoMfDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmMoMfDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmMoMfDTOS,"查找成功");
        }
        return result;
    }
}
