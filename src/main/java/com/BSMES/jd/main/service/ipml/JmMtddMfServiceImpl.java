package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmMtddMfDao;
import com.BSMES.jd.main.dto.JmDevMtidDTO;
import com.BSMES.jd.main.dto.JmMtdd;
import com.BSMES.jd.main.dto.JmMtdd2TfDTO;
import com.BSMES.jd.main.dto.JmMtddMfDTO;
import com.BSMES.jd.main.entity.JmDevMtidEntity;
import com.BSMES.jd.main.entity.JmMtddMfEntity;
import com.BSMES.jd.main.entity.JmMtdd2TfEntity;
import com.BSMES.jd.main.service.JmDevMtidService;
import com.BSMES.jd.main.service.JmMtdd2TfService;
import com.BSMES.jd.main.service.JmMtddMfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JmMtddMfServiceImpl extends BaseServiceImpl<JmMtddMfDao , JmMtddMfEntity , JmMtddMfDTO> implements JmMtddMfService {

    @Autowired
    JmMtdd2TfService jmMtdd2TfService;

    @Autowired
    JmDevMtidService jmDevMtidService;

    @Override
    public void beforeInsert(JmMtddMfDTO dto) {
        dto.setHpdate(new Date());
    }

    @Override
    public void beforEedit(JmMtddMfDTO dto) {

    }

    @Override
    public CommonReturn getMtdd(JmMtddMfDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto,true);
        List<JmMtddMfDTO> mtddMfDTOS = this.select(data);
        if(mtddMfDTOS.isEmpty()){
            result.setAll(20000,null,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,mtddMfDTOS,"查找成功");
        }
        return result;
    }

    /**
     * 老板专用接口
     * @param dto
     * @return
     */
    @Override
    public CommonReturn getMtddPlus(JmMtddMfDTO dto) {
        CommonReturn result = new CommonReturn();
        List<JmMtdd> jmMtdds = new ArrayList<>();
        Map<String,Object> data = MyUtils.objectToMap(dto,true);
        List<JmMtddMfDTO> mtddMfDTOS = this.select(data);
        if (dto!=null && dto.getJbNo()!=null){
            for (JmMtddMfDTO mtddMfDTO : mtddMfDTOS){
                JmMtdd jmMtdd = new JmMtdd();
                QueryWrapper<JmMtdd2TfEntity> jmMtstd2TfEntityQueryWrapper = new QueryWrapper<>();
                jmMtstd2TfEntityQueryWrapper.eq("sid",mtddMfDTO.getSid());
                List<JmMtdd2TfDTO> jmMtdd2TfDTO = jmMtdd2TfService.select(jmMtstd2TfEntityQueryWrapper);
                QueryWrapper<JmDevMtidEntity> jmDevMtidEntityQueryWrapper = new QueryWrapper<>();
                jmDevMtidEntityQueryWrapper.eq("devid",mtddMfDTO.getDevid());
                List<JmDevMtidDTO> jmDevMtidDTO = jmDevMtidService.select(jmDevMtidEntityQueryWrapper);
                if (jmMtdd2TfDTO==null || jmMtdd2TfDTO.size()<=0){
                    jmMtdd.setJmDevMtidDTO(jmDevMtidDTO);
                }else{
                    jmMtdd.setJmMtdd2TfDTOS(jmMtdd2TfDTO);
                }
                jmMtdd.setJmMtddMfDTO(mtddMfDTO);
                jmMtdds.add(jmMtdd);
            }
            result.setAll(20000,jmMtdds,"操作成功");
            return result;
        }
        result.setAll(20000,mtddMfDTOS,"操作成功");

        return result;
    }

    @Override
    public CommonReturn getMtddReport(JmMtddMfDTO dto) {
        CommonReturn result = new CommonReturn();
        List<JmMtdd> jmMtdds = new ArrayList<>();
        Map<String,Object> data = MyUtils.objectToMap(dto,true);
        List<JmMtddMfDTO> mtddMfDTOS = this.select(data);
        if (dto!=null && dto.getJbNo()!=null){
            for (JmMtddMfDTO mtddMfDTO : mtddMfDTOS){
                JmMtdd jmMtdd = new JmMtdd();
                QueryWrapper<JmMtdd2TfEntity> jmMtstd2TfEntityQueryWrapper = new QueryWrapper<>();
                jmMtstd2TfEntityQueryWrapper.eq("sid",mtddMfDTO.getSid());
                List<JmMtdd2TfDTO> jmMtdd2TfDTO = jmMtdd2TfService.select(jmMtstd2TfEntityQueryWrapper);
                QueryWrapper<JmDevMtidEntity> jmDevMtidEntityQueryWrapper = new QueryWrapper<>();
                jmDevMtidEntityQueryWrapper.eq("devid",mtddMfDTO.getDevid());
                List<JmDevMtidDTO> jmDevMtidDTO = jmDevMtidService.select(jmDevMtidEntityQueryWrapper);
                jmMtdd.setJmDevMtidDTO(jmDevMtidDTO);
                jmMtdd.setJmMtdd2TfDTOS(jmMtdd2TfDTO);
                jmMtdd.setJmMtddMfDTO(mtddMfDTO);
                jmMtdds.add(jmMtdd);
            }
        }
        result.setAll(20000,mtddMfDTOS,"操作成功");

        return result;
    }

    @Override
    public CommonReturn saveMtdd(JmMtddMfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            QueryWrapper<JmMtddMfEntity> jmMtddMfEntityQueryWrapper = new QueryWrapper<>();
            jmMtddMfEntityQueryWrapper.eq("sid",dto.getSid());
            JmMtddMfDTO jmMtddMfDTO = this.selectOne(jmMtddMfEntityQueryWrapper);
            //判断 usrcode 是否重复
            if (jmMtddMfDTO==null || jmMtddMfDTO.getSid()==null){
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
    public CommonReturn editMtdd(JmMtddMfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            //获取原先的人员属性值
            QueryWrapper<JmMtddMfEntity> jmMtddMfEntityQueryWrapper = new QueryWrapper<>();
            jmMtddMfEntityQueryWrapper.eq("sid",dto.getSid());
            JmMtddMfDTO jmMtddMfDTO = this.selectOne(jmMtddMfEntityQueryWrapper);
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
    public CommonReturn delMtdd(List<String> sids) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmMtddMfEntity> jmMtddMfEntityQueryWrapper = new QueryWrapper<>();
        jmMtddMfEntityQueryWrapper.in("sid",sids);
        try{
            this.remove(jmMtddMfEntityQueryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CommonReturn getMtddPage(JmMtddMfDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        List<JmMtddMfDTO> jmMtddMfDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmMtddMfDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmMtddMfDTOS,"查找成功");
        }
        return result;
    }
}
