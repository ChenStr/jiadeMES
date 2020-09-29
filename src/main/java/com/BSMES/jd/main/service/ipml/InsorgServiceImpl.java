package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.InsorgDao;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.entity.InsorgEntity;
import com.BSMES.jd.main.service.InsorgService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InsorgServiceImpl extends BaseServiceImpl<InsorgDao , InsorgEntity , InsorgDTO> implements InsorgService {
    @Override
    public void beforeInsert(InsorgDTO dto) {
        //首先先将cCorp默认值加上
        dto.setCCorp(0);
    }

    @Override
    public void beforEedit(InsorgDTO dto) {

    }

    @Override
    public CommonReturn getSorg(InsorgDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto);
        List<InsorgDTO> sorgs = this.select(data);
        if(sorgs.isEmpty()){
            result.setAll(20000,sorgs,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,sorgs,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveSorg(InsorgDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的usrcode是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getOrgcode())){
            QueryWrapper<InsorgEntity> sorgQueryWrapper = new QueryWrapper<>();
            sorgQueryWrapper.eq("orgcode",dto.getOrgcode());
            InsorgDTO sorg = this.selectOne(sorgQueryWrapper);
            //判断 usrcode 是否重复
            if (sorg==null || sorg.getOrgcode()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"部门号已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn editSorg(InsorgDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getOrgcode())){
            //获取原先的用户属性值
            QueryWrapper<InsorgEntity> sorgqueryWrapper = new QueryWrapper<>();
            sorgqueryWrapper.eq("orgcode",dto.getOrgcode());
            InsorgDTO var = this.selectOne(sorgqueryWrapper);
            //设置用户不能操作的属性
            dto.setCCorp(var.getCCorp());
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
    public CommonReturn delSorg(List<String> snames) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<InsorgEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("orgcode",snames);
        try{
            this.remove(queryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getSorgPage(InsorgDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        List<InsorgDTO> insorgDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (insorgDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,insorgDTOS,"查找成功");
        }
        return result;
    }

    @Override
    public InsorgDTO getTest(String id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("orgcode",id);
        InsorgDTO insorgDTO =  selectOne(queryWrapper);
        return insorgDTO;
    }
}
