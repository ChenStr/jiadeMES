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
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

    @Transactional
    @Override
    public CommonReturn saveXjMf(JmXjMf2 dto) {
        CommonReturn result = new CommonReturn();
        Boolean flag = true;
        //首先判断是添加还是修改
        QueryWrapper<JmXjMfEntity> jmXjMfEntityQueryWrapper = new QueryWrapper<>();
        jmXjMfEntityQueryWrapper.eq("sid",dto.getJmXjMfDTO().getSid());
        //检查单是否存在
        JmXjMfDTO jmXjMfDTO = this.selectOne(jmXjMfEntityQueryWrapper);
        if (jmXjMfDTO!=null && jmXjMfDTO.getSid()!=null){
            flag=false;
        }else{
            this.insert(dto.getJmXjMfDTO());
        }
        //如果是编辑的话
        if (flag==false){
            //删除所有原始数据
            QueryWrapper<JmXj2TfEntity> jmXj2TfEntityQueryWrapper = new QueryWrapper<>();
            jmXj2TfEntityQueryWrapper.eq("sid",dto.getJmXjMfDTO().getSid());
            QueryWrapper<JmXj3TfEntity> jmXj3TfEntityQueryWrapper = new QueryWrapper<>();
            jmXj3TfEntityQueryWrapper.eq("sid",dto.getJmXjMfDTO().getSid());
            jmXj2TfService.remove(jmXj2TfEntityQueryWrapper);
            jmXj3TfService.remove(jmXj3TfEntityQueryWrapper);
        }
        //将新的数据添加进去
        for (JmXjMf jmXjMf : dto.getJmXjMfs()){
            if (jmXjMf.getJmXj2TfDTO()!=null && jmXjMf.getJmXj2TfDTO().getSid()!=null){
                jmXj2TfService.saveXj2Tf(jmXjMf.getJmXj2TfDTO());
                QueryWrapper<JmXj3TfEntity> jmXj3TfEntityQueryWrapper = new QueryWrapper<>();
                jmXj3TfEntityQueryWrapper.eq("sid",jmXjMf.getJmXj2TfDTO().getSid());
                List<JmXj3TfDTO> jmXj3TfDTOS = jmXj3TfService.select(jmXj3TfEntityQueryWrapper);
                jmXj3TfService.saveXj3Tfs(jmXj3TfDTOS);
            }
        }

        return result;
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
        List<JmXjMfDTO> jmXjMfDTOS = this.select(xjMfQueryWrapper);
        if (jmXjMfDTOS!=null && jmXjMfDTOS.size()>0){
            for (JmXjMfDTO jmXjMfDTO : jmXjMfDTOS){
                QueryWrapper<JmXj2TfEntity> jmXj2TfEntityQueryWrapper = new QueryWrapper<>();
                QueryWrapper<JmXj3TfEntity> jmXj3TfEntityQueryWrapper = new QueryWrapper<>();
                //删除子单据
                jmXj2TfEntityQueryWrapper.eq("sid",jmXjMfDTO.getSid());
                jmXj3TfEntityQueryWrapper.eq("sid",jmXjMfDTO.getSid());
                jmXj2TfService.remove(jmXj2TfEntityQueryWrapper);
                jmXj3TfService.remove(jmXj3TfEntityQueryWrapper);
            }
        }
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

    @Override
    public CommonReturn getXjMfPlusPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        if (dto.getDescOrder()==null && dto.getAscOrder()==null){
            dto.setDescOrder("hpdate");
        }
        if (dto.getPage()==null){
            dto.setPage(1);
        }
        if (dto.getPageSize()==null){
            dto.setPageSize(10);
        }

        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        List<JmXjMf2> jmXjMf2s = (List<JmXjMf2>) this.getXjMfPlus(dto).getData();

        PageInfo jobPages = new PageInfo<JmXjMf2>(jmXjMf2s);
        result.setAll(20000,jobPages,"操作成功");
        return result;
    }
}
