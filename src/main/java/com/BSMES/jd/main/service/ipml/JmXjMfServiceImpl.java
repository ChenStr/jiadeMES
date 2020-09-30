package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmXjMfDao;
import com.BSMES.jd.main.dto.JmWorkerDTO;
import com.BSMES.jd.main.dto.JmXjMfDTO;
import com.BSMES.jd.main.dto.JmXjTfDTO;
import com.BSMES.jd.main.dto.XjMtf;
import com.BSMES.jd.main.entity.JmWorkerEntity;
import com.BSMES.jd.main.entity.JmXjMfEntity;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.main.service.JmXjMfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class JmXjMfServiceImpl extends BaseServiceImpl<JmXjMfDao , JmXjMfEntity , JmXjMfDTO> implements JmXjMfService {

    @Autowired
    InssysvarService inssysvarService;

    @Override
    public void beforeInsert(JmXjMfDTO dto) {

    }

    @Override
    public void beforEedit(JmXjMfDTO dto) {

    }

    @Override
    public CommonReturn getXjMf(JmXjMfDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto);
        List<JmXjMfDTO> xjMfs = this.select(data);
        if(xjMfs.isEmpty()){
            result.setAll(20000,xjMfs,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,xjMfs,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveXjMf(JmXjMfDTO dto) {
        CommonReturn result = new CommonReturn();
        dto.setSid(this.getKey("JmXjMf","sid",inssysvarService,dto));
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            QueryWrapper<JmXjMfEntity> xjMfQueryWrapper = new QueryWrapper<>();
            xjMfQueryWrapper.eq("sid",dto.getSid());
            JmXjMfDTO xjMfs = this.selectOne(xjMfQueryWrapper);
            //判断 usrcode 是否重复
            if (xjMfs==null || xjMfs.getSid()==null){
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

    @Transactional
    @Override
    public CommonReturn saveXjMtf(XjMtf dto) {
        JmXjMfDTO xjMf = dto.getXjMf();
        List<JmXjTfDTO> xjtf = dto.getXjTf();
        return null;
    }

    @Override
    public CommonReturn editXjMf(JmXjMfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            //获取原先的人员属性值
            QueryWrapper<JmXjMfEntity> xjMfQueryWrapper = new QueryWrapper<>();
            xjMfQueryWrapper.eq("sid",dto.getSid());
            JmXjMfDTO worker = this.selectOne(xjMfQueryWrapper);
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
    public CommonReturn delXjMf(List<String> sids) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmXjMfEntity> xjMfQueryWrapper = new QueryWrapper<>();
        xjMfQueryWrapper.in("sid",sids);
        try{
            this.remove(xjMfQueryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getXjMfPage(JmXjMfDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        List<JmXjMfDTO> jmXjMfDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmXjMfDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmXjMfDTOS,"查找成功");
        }
        return result;
    }
}
