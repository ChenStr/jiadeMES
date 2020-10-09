package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmXjMfDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.JmXj2TfEntity;
import com.BSMES.jd.main.entity.JmXj3TfEntity;
import com.BSMES.jd.main.entity.JmXjMfEntity;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.main.service.JmXj2TfService;
import com.BSMES.jd.main.service.JmXj3TfService;
import com.BSMES.jd.main.service.JmXjMfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class JmXjMfServiceImpl extends BaseServiceImpl<JmXjMfDao , JmXjMfEntity , JmXjMfDTO> implements JmXjMfService {

    @Autowired
    InssysvarService inssysvarService;

    @Autowired
    JmXj2TfService jmXj2TfService;

    @Autowired
    JmXj3TfService jmXj3TfService;

    @Autowired
    JmXjMfDao jmXjMfDao;

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
    public CommonReturn getXjMfPlus(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmXjMf2> jmXjMfDTOS = jmXjMfDao.getJmXjMf2(dto);
        //查询子表与其他信息
        for (JmXjMf2 jmXjMf2 : jmXjMfDTOS){
            List<JmXjMf> jmXjMfs = new ArrayList<>();
            JmXjMf jmXjMf = new JmXjMf();
            List<JmXj2TfDTO> jmXj2TfDTOs = new ArrayList<>();
            QueryWrapper<JmXj2TfEntity> jmXj2TfEntityQueryWrapper = new QueryWrapper<>();
            jmXj2TfEntityQueryWrapper.eq("sid",jmXjMf2.getJmXjMfDTO().getSid());
            jmXj2TfDTOs = jmXj2TfService.select(jmXj2TfEntityQueryWrapper);
            for (JmXj2TfDTO jmXj2TfDTO : jmXj2TfDTOs){
                List<JmXj3TfDTO> jmXj3TfDTOS = new ArrayList<>();
                QueryWrapper<JmXj3TfEntity> jmXj3TfEntityQueryWrapper = new QueryWrapper<>();
                jmXj3TfEntityQueryWrapper.eq("sid",jmXj2TfDTO.getSid()).eq("cid",jmXj2TfDTO.getCid());
                jmXj3TfDTOS = jmXj3TfService.select(jmXj3TfEntityQueryWrapper);
                jmXjMf.setJmXj2TfDTO(jmXj2TfDTO);
                jmXjMf.setJmXj3TfDTOS(jmXj3TfDTOS);
                jmXjMfs.add(jmXjMf);
            }
            jmXjMf2.setJmXjMfs(jmXjMfs);
        }
        result.setAll(20000,jmXjMfDTOS,"操作成功");
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
