package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmXjMfDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.*;
import com.BSMES.jd.main.service.*;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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
    JmJobService jmJobService;

    @Autowired
    JmXjMfDao jmXjMfDao;

    @Override
    public void beforeInsert(JmXjMfDTO dto) {
        dto.setHpdate(new Date());
        dto.setSbuid("XJ");
    }

    @Override
    public void beforEedit(JmXjMfDTO dto) {

    }

    @Override
    public CommonReturn getXjMf(JmXjMfDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto,true);
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
        List<JmXjMf2> jmXjMfDTOS = jmXjMfDao.getJmXjMf2(dto);;
        if (jmXjMfDTOS==null && jmXjMfDTOS.size()==0){
            jmXjMfDTOS = new ArrayList<>();
            result.setAll(20000,jmXjMfDTOS,"未查到数据");
            return result;
        }
        //查询子表与其他信息
        for (JmXjMf2 jmXjMf2 : jmXjMfDTOS){
            List<JmXjMf> jmXjMfs = new ArrayList<>();
            List<JmXj2TfDTO> jmXj2TfDTOs = new ArrayList<>();
            QueryWrapper<JmXj2TfEntity> jmXj2TfEntityQueryWrapper = new QueryWrapper<>();
            jmXj2TfEntityQueryWrapper.eq("sid",jmXjMf2.getJmXjMfDTO().getSid());
            jmXj2TfDTOs = jmXj2TfService.select(jmXj2TfEntityQueryWrapper);
            //查询子表的子表信息
            if(jmXj2TfDTOs!=null && jmXj2TfDTOs.size()>0){
                //查询Job表的数据
                QueryWrapper<JmJobEntity> jmJobEntityQueryWrapper = new QueryWrapper<>();
                jmJobEntityQueryWrapper.eq("jb_no",jmXjMf2.getJmXjMfDTO().getJbNo()).select("sid","jb_no","state","create_date");
                JmJobDTO jmJobDTO = jmJobService.selectOne(jmJobEntityQueryWrapper);
                jmXjMf2.setJmJobDTO(jmJobDTO);
                jmXjMf2.setJmXjMfs(jmXjMfs);
            }
        }
        result.setAll(20000,jmXjMfDTOS,"操作成功");
        return result;
    }

    @Override
    public CommonReturn saveXjMf(JmXjMf2 dto) {
        CommonReturn result = new CommonReturn();
        Boolean flag = true;
        String sid = null;

        //首先判断是添加还是修改
        if (dto.getJmXjMfDTO()!=null && dto.getJmXjMfDTO().getSid()!=null){
            flag=false;
            sid = dto.getJmXjMfDTO().getSid();
            this.editXjMf(dto.getJmXjMfDTO());
        }else{
            sid = this.getKey("JmXjMf","sid",inssysvarService,dto.getJmXjMfDTO());
            dto.getJmXjMfDTO().setSid(sid);
            this.insert(dto.getJmXjMfDTO());
        }

        try{
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
                jmXjMf.getJmXj2TfDTO().setSid(sid);
                if (jmXjMf.getJmXj2TfDTO()!=null && jmXjMf.getJmXj2TfDTO().getSid()!=null){
                    jmXj2TfService.saveXj2Tf(jmXjMf.getJmXj2TfDTO());
                }
                if (jmXjMf.getJmXj3TfDTOS()!=null && jmXjMf.getJmXj3TfDTOS().size()>0){
                    //将数值为空的进行筛选
                    for (JmXj3TfDTO jmXj3TfDTO : jmXjMf.getJmXj3TfDTOS()){
                        if (jmXj3TfDTO.getChkviation()==null){
                            jmXjMf.getJmXj3TfDTOS().remove(jmXj3TfDTO);
                        }
                    }
                    String sid1 = sid;
                    jmXjMf.getJmXj3TfDTOS().stream().forEach(t->t.setSid(sid1));
                    jmXj3TfService.saveXj3Tfs(jmXjMf.getJmXj3TfDTOS());
                }
            }
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CommonReturn saveXjMfs(List<JmXjMfDTO> dtos) {
        CommonReturn result = new CommonReturn();
        //将sid插入
        for(int i = 0 ; i < dtos.size() ; i++){
            //判定调度单号(制令单)是否存在 sid
            String key = this.getKey("JmXjMf","sid",inssysvarService,dtos.get(i));
            //首先先找到编码规则
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("sname","JmXjMf");
            String code = inssysvarService.selectOne(queryWrapper).getSbds();
            //获取括号前的数据
            String after = code.substring(code.indexOf('%')+1,code.length());
            String before = key.substring(0,key.length() - after.length());
            String keyafter = key.substring(key.length()-after.length(),key.length());
            key = before + MyUtils.geFourNumber(Integer.parseInt(keyafter)+i,after.length());
            dtos.get(i).setSid(key);
            //删除所有的 jb_no 的数据
            QueryWrapper<JmXjMfEntity> jmXjMfEntityQueryWrapper = new QueryWrapper<>();
            jmXjMfEntityQueryWrapper.eq("jb_no",dtos.get(i).getJbNo());
            this.remove(jmXjMfEntityQueryWrapper);
        }
        try{
            jmXjMfDao.saveJmXjMfs(dtos);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
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
        IPage<JmXjMfDTO> jmXjMfDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
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
        List<JmXjMf2> data = (List<JmXjMf2>) this.getXjMfPlus(dto).getData();
        PageInfo dataPages = new PageInfo<JmXjMf2>(data);
        result.setAll(20000,dataPages,"操作成功");
        return result;
    }
}
