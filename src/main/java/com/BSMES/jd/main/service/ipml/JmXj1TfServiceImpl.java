package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmXj1TfDao;
import com.BSMES.jd.main.dto.JmXj1TfDTO;
import com.BSMES.jd.main.dto.JmXjMfDTO;
import com.BSMES.jd.main.entity.JmXj1TfEntity;
import com.BSMES.jd.main.entity.JmXjMfEntity;
import com.BSMES.jd.main.service.JmXj1TfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JmXj1TfServiceImpl extends BaseServiceImpl<JmXj1TfDao , JmXj1TfEntity , JmXj1TfDTO> implements JmXj1TfService {
    @Override
    public void beforeInsert(JmXj1TfDTO dto) {

    }

    @Override
    public void beforEedit(JmXj1TfDTO dto) {

    }

    @Override
    public CommonReturn getXj1Tf(JmXj1TfDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto);
        List<JmXj1TfDTO> xj1Tfs = this.select(data);
        if(xj1Tfs.isEmpty()){
            result.setAll(20000,xj1Tfs,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,xj1Tfs,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveXj1Tf(JmXj1TfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            QueryWrapper<JmXj1TfEntity> xj1TfQueryWrapper = new QueryWrapper<>();
            xj1TfQueryWrapper.eq("spc_no",dto.getSpcNo());
            JmXj1TfDTO xjMfs = this.selectOne(xj1TfQueryWrapper);
            //判断 usrcode 是否重复
            if (xjMfs==null || xjMfs.getSpcNo()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"检验单号已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn editXj1Tf(JmXj1TfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            //获取原先的人员属性值
            QueryWrapper<JmXj1TfEntity> xj1TfQueryWrapper = new QueryWrapper<>();
            xj1TfQueryWrapper.eq("spc_no",dto.getSpcNo());
            JmXj1TfDTO xjMfs = this.selectOne(xj1TfQueryWrapper);
            //设置用户不能操作的属性
            try{
                this.edit(dto);
                result.setAll(20000,null,"操作成功");
            }catch (Exception e){
                result.setAll(10001,null,"操作失败");
                e.printStackTrace();
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn delXj1Tf(List<String> socNos) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmXj1TfEntity> xj1TfQueryWrapper = new QueryWrapper<>();
        xj1TfQueryWrapper.in("spc_no",socNos);
        try{
            this.remove(xj1TfQueryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(20000, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getXj1TfPage(JmXj1TfDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        List<JmXj1TfDTO> jmXj1TfDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmXj1TfDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmXj1TfDTOS,"查找成功");
        }
        return result;
    }
}
