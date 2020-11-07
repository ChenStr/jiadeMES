package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmMdbkTfDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.JmMdbkMfEntity;
import com.BSMES.jd.main.entity.JmMdbkTfEntity;
import com.BSMES.jd.main.entity.JmMdlyMfEntity;
import com.BSMES.jd.main.service.JmMdbkMfService;
import com.BSMES.jd.main.service.JmMdbkTfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JmMdbkTfServiceImpl extends BaseServiceImpl<JmMdbkTfDao, JmMdbkTfEntity, JmMdbkTfDTO> implements JmMdbkTfService {

    @Autowired
    JmMdbkTfDao jmMdbkTfDao;

    @Autowired
    JmMdbkMfService jmMdbkMfService;

    @Override
    public void beforeInsert(JmMdbkTfDTO dto) {

    }

    @Override
    public void beforEedit(JmMdbkTfDTO dto) {

    }

    @Override
    public CommonReturn getMdbkTf(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmMdbkTfDTO> jmMdbkTfDTOS = this.select(this.getQueryWrapper(dto));
        if(jmMdbkTfDTOS.isEmpty()){
            result.setAll(20000,jmMdbkTfDTOS,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,jmMdbkTfDTOS,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn getMdbk(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmMdbkTfDTO> dtos = this.select(this.getQueryWrapper(dto));
        List<JmMdbk> jmMdbks = new ArrayList<>();
        if(dtos.isEmpty()){
            result.setAll(20000,dtos,"没有查找结果，建议仔细核对查找条件");
        }else{
            for (JmMdbkTfDTO dto1 :dtos){
                JmMdbk jmMdbk = new JmMdbk();
                List<JmMdbkTfDTO> jmMdbkTfDTOS = new ArrayList<>();
                jmMdbkTfDTOS.add(dto1);
                QueryWrapper<JmMdbkMfEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("sid",dto1.getSid());
                JmMdbkMfDTO jmMdbkTfDTO = jmMdbkMfService.selectOne(queryWrapper);
                jmMdbk.setJmMdbkMfDTO(jmMdbkTfDTO);
                jmMdbk.setJmMdbkTfDTOS(jmMdbkTfDTOS);
                jmMdbks.add(jmMdbk);
            }
            result.setAll(20000,jmMdbks,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveMdbkTf(JmMdbkTfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的usrcode是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            QueryWrapper<JmMdbkTfEntity> jmMdbkTfEntityQueryWrapper = new QueryWrapper<>();
            jmMdbkTfEntityQueryWrapper.eq("sid",dto.getSid());
            JmMdbkTfDTO jmMdbkTfDTO = this.selectOne(jmMdbkTfEntityQueryWrapper);
            //判断 usrcode 是否重复
            if (jmMdbkTfDTO==null || jmMdbkTfDTO.getSid()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"模具还回单号已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn saveMdbkTfs(List<JmMdbkTfDTO> dtos) {
        CommonReturn result = new CommonReturn();
        try{
            jmMdbkTfDao.saveJmMdbkTfs(dtos);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CommonReturn editMdbkTf(JmMdbkTfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            //获取原先的用户属性值
            QueryWrapper<JmMdbkTfEntity> jmMdbkMfEntityQueryWrapper = new QueryWrapper<>();
            jmMdbkMfEntityQueryWrapper.eq("sid",dto.getSid());
            JmMdbkTfDTO var = this.selectOne(jmMdbkMfEntityQueryWrapper);
            try{
//                this.edit(dto);
                jmMdbkTfDao.editJmMdbkTf(dto);
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
    public CommonReturn delMdbkTf(List<String> sids, List<Integer> cids) {
        CommonReturn result = new CommonReturn();
        if (sids!=null && cids!=null && sids.size()!=0 && cids.size()!=0 && sids.size()==cids.size()){
            for (int i = 0 ; i < sids.size() ; i++){
                QueryWrapper<JmMdbkTfEntity> jmMdbkTfEntityQueryWrapper = new QueryWrapper<>();
                jmMdbkTfEntityQueryWrapper.eq("sid",sids.get(i));
                jmMdbkTfEntityQueryWrapper.eq("cid",cids.get(i));
                try{
                    this.remove(jmMdbkTfEntityQueryWrapper);
                    result.setAll(20000,null,"操作成功");
                }catch (Exception e) {
                    result.setAll(10001, null, "操作失败");
                    return result;
                }
            }

        }else{
            result.setAll(10001,null,"操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getMdbkTfPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<JmMdbkTfDTO> jmMdbkTfDTOIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        if (jmMdbkTfDTOIPage==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmMdbkTfDTOIPage,"查找成功");
        }
        return result;
    }

    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();

        if(dto.getAscOrder()==null && dto.getDescOrder()==null){
            dto.setDescOrder("hpdate");
        }

        if (MyUtils.StringIsNull(dto.getSid())){
            queryWrapper.eq("sid",dto.getSid());
        }
        if (MyUtils.StringIsNull(dto.getMouldName())){
            queryWrapper.eq("md_name",dto.getMouldName());
        }
        if (MyUtils.StringIsNull(dto.getMouldNo())){
            queryWrapper.eq("md_no",dto.getMouldNo());
        }
        if (MyUtils.StringIsNull(dto.getType())){
            queryWrapper.eq("typeid",dto.getType());
        }
        if (dto.getBegDd()!=null){
            queryWrapper.ge("hpdate",dto.getBegDd());
        }
        if(dto.getEndDd()!=null){
            queryWrapper.le("hpdate",dto.getEndDd());
        }

        if (dto.getAscOrder()!=null){
            queryWrapper.orderByAsc(MyUtils.humpToLine((String) dto.getAscOrder()));
        }
        if (dto.getDescOrder()!=null && dto.getAscOrder()==null){
            queryWrapper.orderByDesc(MyUtils.humpToLine((String) dto.getDescOrder()));
        }


        return queryWrapper;
    }
}
