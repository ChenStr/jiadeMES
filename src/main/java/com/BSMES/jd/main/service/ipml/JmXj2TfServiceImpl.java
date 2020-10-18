package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmXj2TfDao;
import com.BSMES.jd.main.dao.JmXj3TfDao;
import com.BSMES.jd.main.dto.JmMouldDTO;
import com.BSMES.jd.main.dto.JmXj2TfDTO;
import com.BSMES.jd.main.dto.JmXj3TfDTO;
import com.BSMES.jd.main.dto.JmXjMf;
import com.BSMES.jd.main.entity.JmMouldEntity;
import com.BSMES.jd.main.entity.JmXj2TfEntity;
import com.BSMES.jd.main.service.JmXj2TfService;
import com.BSMES.jd.main.service.JmXj3TfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JmXj2TfServiceImpl extends BaseServiceImpl<JmXj2TfDao , JmXj2TfEntity , JmXj2TfDTO> implements JmXj2TfService {

    @Autowired
    JmXj2TfDao jmXj2TfDao;

    @Autowired
    JmXj3TfDao jmXj3TfDao;

    @Autowired
    JmXj3TfService jmXj3TfService;


    @Override
    public void beforeInsert(JmXj2TfDTO dto) {

    }

    @Override
    public void beforEedit(JmXj2TfDTO dto) {

    }

    @Override
    public CommonReturn getXj2Tf(JmXj2TfDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto,true);
        List<JmXj2TfDTO> xj2Tfs = this.select(data);
        if(xj2Tfs.isEmpty()){
            result.setAll(20000,xj2Tfs,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,xj2Tfs,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveXj2Tf(JmXj2TfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 md_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid()) && dto.getCid()!=null ){
            QueryWrapper<JmXj2TfEntity> jmXj2TfQueryWrapper = new QueryWrapper<>();
            jmXj2TfQueryWrapper.eq("sid",dto.getSid());
            jmXj2TfQueryWrapper.eq("cid",dto.getCid());
            JmXj2TfDTO jmXj2TfDTO = this.selectOne(jmXj2TfQueryWrapper);
            //判断 usrcode 是否重复
            if (jmXj2TfDTO==null || jmXj2TfDTO.getSid()==null){
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
    public CommonReturn saveXj2TfAndXj3Tf(JmXjMf dto) {
        CommonReturn result = new CommonReturn();
        try{
            this.saveXj2Tf(dto.getJmXj2TfDTO());
            jmXj3TfService.saveXj3Tfs(dto.getJmXj3TfDTOS());
            result.setAll(20000,null,"保存成功");
        }catch (Exception e){
            result.setAll(40000,null,"保存失败");
        }
        return result;
    }

    @Override
    public CommonReturn saveXj2Tfs(List<JmXj2TfDTO> dtos) {
        CommonReturn result = new CommonReturn();
        try {
            jmXj2TfDao.saveJmXj2Tfs(dtos);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(20000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CommonReturn editXj2Tf(JmXjMf dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 md_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getJmXj2TfDTO().getSid()) && dto.getJmXj2TfDTO().getCid()!=null ){
            //获取原先的人员属性值
            QueryWrapper<JmXj2TfEntity> jmXj2TfQueryWrapper = new QueryWrapper<>();
            jmXj2TfQueryWrapper.eq("sid",dto.getJmXj2TfDTO().getSid());
            jmXj2TfQueryWrapper.eq("cid",dto.getJmXj2TfDTO().getCid());
            JmXj2TfDTO jmXj2TfDTO = this.selectOne(jmXj2TfQueryWrapper);
            //设置用户不能操作的属性
            try{
//                this.edit(dto);
                jmXj2TfDao.editJmXj2Tf(dto.getJmXj2TfDTO());
                for (JmXj3TfDTO jmXj3TfDTO :  dto.getJmXj3TfDTOS()){
                    jmXj3TfDao.editJmXj3Tfs(jmXj3TfDTO);
                }
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
    public CommonReturn checkXj2Tf(JmXjMf dto) {
        CommonReturn result = new CommonReturn();
        try{
            JmXj2TfDTO jmXj2TfDTO = new JmXj2TfDTO();
            jmXj2TfDTO.setSid(dto.getJmXj2TfDTO().getSid());
            jmXj2TfDTO.setCid(dto.getJmXj2TfDTO().getCid());
            jmXj2TfDTO.setState("2");
            jmXj2TfDTO.setQtyMd(dto.getJmXj2TfDTO().getQtyMd());
            jmXj2TfDTO.setOpDd(dto.getJmXj2TfDTO().getOpDd());
            jmXj2TfDao.editJmXj2Tf(jmXj2TfDTO);
            for (JmXj3TfDTO jmXj3TfDTO : dto.getJmXj3TfDTOS()){
                jmXj3TfDao.editJmXj3Tfs(jmXj3TfDTO);
            }
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(20000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CommonReturn delXj2Tf(List<String> sids, List<Integer> cids) {
        CommonReturn result = new CommonReturn();
        //判断长度是否相等
        if (sids!=null && cids!=null && sids.size()!=0 && cids.size()!=0 && sids.size()==cids.size()){
            for (int i = 0 ; i < sids.size() ; i++){
                QueryWrapper<JmXj2TfEntity> jmXj2TfQueryWrapper = new QueryWrapper<>();
                jmXj2TfQueryWrapper.eq("sid",sids.get(i));
                jmXj2TfQueryWrapper.eq("cid",cids.get(i));
                try{
                    this.remove(jmXj2TfQueryWrapper);
                    result.setAll(20000,null,"操作成功");
                }catch (Exception e) {
                    result.setAll(10001, null, "操作失败");
                    e.printStackTrace();
                    return result;
                }
            }

        }else{
            result.setAll(10001,null,"操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getXj2TfPage(JmXj2TfDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        List<JmXj2TfDTO> jmXj2TfDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmXj2TfDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmXj2TfDTOS,"查找成功");
        }
        return result;
    }
}
