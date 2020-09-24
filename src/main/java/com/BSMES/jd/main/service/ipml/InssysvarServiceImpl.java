package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.InssysvarDao;
import com.BSMES.jd.main.dto.InssysvarDTO;
import com.BSMES.jd.main.entity.InssysvarEntity;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class InssysvarServiceImpl extends BaseServiceImpl<InssysvarDao , InssysvarEntity , InssysvarDTO> implements InssysvarService {
    @Override
    public void beforeInsert(InssysvarDTO dto) {
        //首先先将cCorp默认值加上
        dto.setCCorp(0);
    }

    @Override
    public void beforEedit(InssysvarDTO dto) {

    }

    @Override
    public CommonReturn getVar(InssysvarDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto);
        List<InssysvarDTO> vars = this.select(data);
        if(vars.isEmpty()){
            result.setAll(20000,vars,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,vars,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveVar(InssysvarDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的usrcode是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSname())){
            QueryWrapper<InssysvarEntity> varQueryWrapper = new QueryWrapper<>();
            varQueryWrapper.eq("sname",dto.getSname());
            InssysvarDTO var = this.selectOne(varQueryWrapper);
            //判断 usrcode 是否重复
            if (var==null || var.getSname()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"变量已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn editVar(InssysvarDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSname())){
            //获取原先的用户属性值
            QueryWrapper<InssysvarEntity> varqueryWrapper = new QueryWrapper<>();
            varqueryWrapper.eq("usrcode",dto.getSname());
            InssysvarDTO var = this.selectOne(varqueryWrapper);
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
    public CommonReturn delVar(List<String> snames) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<InssysvarEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("sname",snames);
        try{
            this.remove(queryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(20000, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getVarPage(InssysvarDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        List<InssysvarDTO> inssysvarDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (inssysvarDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,inssysvarDTOS,"查找成功");
        }
        return result;
    }
}
