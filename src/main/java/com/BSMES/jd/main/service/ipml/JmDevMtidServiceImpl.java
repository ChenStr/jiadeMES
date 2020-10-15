package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmDevMtidDao;
import com.BSMES.jd.main.dto.JmDevMtidDTO;
import com.BSMES.jd.main.entity.JmDevMtidEntity;
import com.BSMES.jd.main.service.JmDevMtidService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JmDevMtidServiceImpl extends BaseServiceImpl<JmDevMtidDao, JmDevMtidEntity , JmDevMtidDTO> implements JmDevMtidService {
    @Override
    public void beforeInsert(JmDevMtidDTO dto) {

    }

    @Override
    public void beforEedit(JmDevMtidDTO dto) {

    }

    @Override
    public CommonReturn getDevMtid(JmDevMtidDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto,true);
        List<JmDevMtidDTO> moulds = this.select(data);
        if(moulds.isEmpty()){
            result.setAll(20000,moulds,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,moulds,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveDevMtid(JmDevMtidDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 md_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getDevid()) && dto.getMtId()!=null){
            QueryWrapper<JmDevMtidEntity> jmDevMtidEntityQueryWrapper = new QueryWrapper<>();
            jmDevMtidEntityQueryWrapper.eq("devid",dto.getDevid());
            jmDevMtidEntityQueryWrapper.eq("mt_id",dto.getMtId());
            JmDevMtidDTO JmMould = this.selectOne(jmDevMtidEntityQueryWrapper);
            //判断 usrcode 是否重复
            if (JmMould==null || JmMould.getDevid()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"模具号已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn editDevMtid(JmDevMtidDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 md_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getDevid()) && dto.getMtId()!=null ){
            //获取原先的人员属性值
            QueryWrapper<JmDevMtidEntity> jmDevMtidEntityQueryWrapper = new QueryWrapper<>();
            jmDevMtidEntityQueryWrapper.eq("md_no",dto.getDevid());
            jmDevMtidEntityQueryWrapper.eq("typeid",dto.getMtId());
            JmDevMtidDTO JmMould = this.selectOne(jmDevMtidEntityQueryWrapper);
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
    public CommonReturn delDevMtid(List<String> devids, List<String> mtIds) {
        CommonReturn result = new CommonReturn();
        //判断长度是否相等
        if (devids!=null && mtIds!=null && devids.size()!=0 && mtIds.size()!=0 && devids.size()==mtIds.size()){
            for (int i = 0 ; i < devids.size() ; i++){
                QueryWrapper<JmDevMtidEntity> jmDevMtidEntityQueryWrapper = new QueryWrapper<>();
                jmDevMtidEntityQueryWrapper.eq("devid",devids.get(i));
                jmDevMtidEntityQueryWrapper.eq("mt_id",mtIds.get(i));
                try{
                    this.remove(jmDevMtidEntityQueryWrapper);
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
    public CommonReturn getDevMtidPage(JmDevMtidDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        List<JmDevMtidDTO> jmDevMtidDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmDevMtidDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmDevMtidDTOS,"查找成功");
        }
        return result;
    }
}
