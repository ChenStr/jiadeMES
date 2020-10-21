package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmPrdtDao;
import com.BSMES.jd.main.dto.JmPrdtDTO;
import com.BSMES.jd.main.entity.JmPrdtEntity;
import com.BSMES.jd.main.service.JmPrdtService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JmPrdtServiceImpl extends BaseServiceImpl<JmPrdtDao , JmPrdtEntity , JmPrdtDTO > implements JmPrdtService {
    @Override
    public void beforeInsert(JmPrdtDTO dto) {

    }

    @Override
    public void beforEedit(JmPrdtDTO dto) {

    }

    @Override
    public CommonReturn getPrdt(JmPrdtDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto,true);
        List<JmPrdtDTO> prdts = this.select(data);
        if(prdts.isEmpty()){
            result.setAll(20000,prdts,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,prdts,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn savePrdt(JmPrdtDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getPrdNo())){
            QueryWrapper<JmPrdtEntity> prdtQueryWrapper = new QueryWrapper<>();
            prdtQueryWrapper.eq("prd_no",dto.getPrdNo());
            JmPrdtDTO prdt = this.selectOne(prdtQueryWrapper);
            //判断 usrcode 是否重复
            if (prdt==null || prdt.getPrdNo()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"货品号已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn editPrdt(JmPrdtDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getPrdNo())){
            //获取原先的人员属性值
            QueryWrapper<JmPrdtEntity> prdtQueryWrapper = new QueryWrapper<>();
            prdtQueryWrapper.eq("prd_no",dto.getPrdNo());
            JmPrdtDTO worker = this.selectOne(prdtQueryWrapper);
            //设置用户不能操作的属性
            try{
                this.edit(dto);
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
    public CommonReturn delPrdt(List<String> prdNos) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmPrdtEntity> prdtQueryWrapper = new QueryWrapper<>();
        prdtQueryWrapper.in("prd_no",prdNos);
        try{
            this.remove(prdtQueryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getPrdtPage(JmPrdtDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        IPage<JmPrdtDTO> jmPrdtDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmPrdtDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmPrdtDTOS,"查找成功");
        }
        return result;
    }
}
