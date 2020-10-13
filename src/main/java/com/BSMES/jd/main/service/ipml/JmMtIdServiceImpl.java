package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmMtIdDao;
import com.BSMES.jd.main.dto.JmMtIdDTO;
import com.BSMES.jd.main.dto.JmWhDTO;
import com.BSMES.jd.main.entity.JmMtIdEntity;
import com.BSMES.jd.main.entity.JmWhEntity;
import com.BSMES.jd.main.service.JmMtIdService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JmMtIdServiceImpl extends BaseServiceImpl<JmMtIdDao , JmMtIdEntity , JmMtIdDTO> implements JmMtIdService {
    @Override
    public void beforeInsert(JmMtIdDTO dto) {

    }

    @Override
    public void beforEedit(JmMtIdDTO dto) {

    }

    @Override
    public CommonReturn getMtId(JmMtIdDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto,true);
        List<JmMtIdDTO> mts = this.select(data);
        if(mts.isEmpty()){
            result.setAll(20000,mts,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,mts,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveMtId(JmMtIdDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getMtId())){
            QueryWrapper<JmMtIdEntity> mtQueryWrapper = new QueryWrapper<>();
            mtQueryWrapper.eq("mt_id",dto.getMtId());
            JmMtIdDTO mt = this.selectOne(mtQueryWrapper);
            //判断 usrcode 是否重复
            if (mt==null || mt.getMtId()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"单号已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn editMtId(JmMtIdDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getMtId())){
            //获取原先的人员属性值
            QueryWrapper<JmMtIdEntity> mtQueryWrapper = new QueryWrapper<>();
            mtQueryWrapper.eq("mt_id",dto.getMtId());
            JmMtIdDTO mt = this.selectOne(mtQueryWrapper);
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
    public CommonReturn delMtId(List<String> mtIds) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmMtIdEntity> mtQueryWrapper = new QueryWrapper<>();
        mtQueryWrapper.in("mt_id",mtIds);
        try{
            this.remove(mtQueryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getMtIdPage(JmMtIdDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        List<JmMtIdDTO> jmMtIdDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmMtIdDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmMtIdDTOS,"查找成功");
        }
        return result;
    }
}
