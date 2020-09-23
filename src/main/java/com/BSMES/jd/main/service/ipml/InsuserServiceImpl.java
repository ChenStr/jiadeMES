package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.InsuserDao;
import com.BSMES.jd.main.dto.InsuserDTO;
import com.BSMES.jd.main.entity.InsuserEntity;
import com.BSMES.jd.main.service.InsuserService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InsuserServiceImpl extends BaseServiceImpl<InsuserDao , InsuserEntity , InsuserDTO> implements InsuserService {

    @Override
    public void beforeInsert(InsuserDTO dto) {
        //首先先将cCorp默认值加上
        dto.setCCorp(0);
    }

    @Override
    public void beforEedit(InsuserDTO dto) {

    }

    @Override
    public CommonReturn getUser(InsuserDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto);
        List<InsuserDTO> users = this.select(data);
        if(users.isEmpty()){
            result.setAll(20000,users,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,users,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveUser(InsuserDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的usrcode是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getUsrcode())){
            QueryWrapper<InsuserEntity> insuserQueryWrapper = new QueryWrapper<>();
            insuserQueryWrapper.eq("usrcode",dto.getUsrcode());
            InsuserDTO user = this.selectOne(insuserQueryWrapper);
            //判断 usrcode 是否重复
            if (user==null || user.getUsrcode()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"用户已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn editUser(InsuserDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getUsrcode())){
            //获取原先的用户属性值
            QueryWrapper<InsuserEntity> insuserqueryWrapper = new QueryWrapper<>();
            insuserqueryWrapper.eq("usrcode",dto.getUsrcode());
            InsuserDTO user = this.selectOne(insuserqueryWrapper);
            //设置用户不能操作的属性
            dto.setCCorp(user.getCCorp());
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
    public CommonReturn delUser(List<String> usrcodes) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<InsuserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("usrcode",usrcodes);
        try{
            this.remove(queryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(20000, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getUserPage(InsuserDTO dto,QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        List<InsuserDTO> insuserDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (insuserDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,insuserDTOS,"查找成功");
        }
        return result;
    }
}
