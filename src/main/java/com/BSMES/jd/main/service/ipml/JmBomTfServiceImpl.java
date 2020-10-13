package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmBomTfDao;
import com.BSMES.jd.main.dto.JmBomMfDTO;
import com.BSMES.jd.main.dto.JmBomTfDTO;
import com.BSMES.jd.main.dto.JmJobDTO;
import com.BSMES.jd.main.dto.JmMoMfDTO;
import com.BSMES.jd.main.entity.JmBomMfEntity;
import com.BSMES.jd.main.entity.JmBomTfEntity;
import com.BSMES.jd.main.entity.JmJobEntity;
import com.BSMES.jd.main.entity.JmMoMfEntity;
import com.BSMES.jd.main.service.JmBomMfService;
import com.BSMES.jd.main.service.JmBomTfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JmBomTfServiceImpl extends BaseServiceImpl<JmBomTfDao , JmBomTfEntity , JmBomTfDTO> implements JmBomTfService {
    @Override
    public void beforeInsert(JmBomTfDTO dto) {

    }

    @Override
    public void beforEedit(JmBomTfDTO dto) {

    }

    @Autowired
    JmBomMfService jmBomMfService;

    @Override
    public CommonReturn getBomMf(JmBomTfDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto,true);
        List<JmBomTfDTO> bomTf = this.select(data);
        if(bomTf.isEmpty()){
            result.setAll(20000,bomTf,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,bomTf,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveBomMf(JmBomTfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getBomNo()) && dto.getItm()!=null ){
            QueryWrapper<JmBomTfEntity> bomTfQueryWrapper = new QueryWrapper<>();
            //判断制令单号是否存在 , 不存在不能添加
            QueryWrapper<JmBomMfEntity> moMfQueryWrapper = new QueryWrapper<>();
            JmBomMfDTO bomMf = jmBomMfService.selectOne(moMfQueryWrapper.eq("bom_no",dto.getBomNo()));
            if (bomMf==null || bomMf.getBomNo() == null){
                result.setAll(10001,null,"Bom号不存在不能新增，不能新增!");
            }else{
                bomTfQueryWrapper.eq("bom_no",dto.getBomNo());
                bomTfQueryWrapper.eq("itm",dto.getItm());
                JmBomTfDTO bomTf = this.selectOne(bomTfQueryWrapper);
                //判断 usrcode 是否重复
                if (bomTf==null || bomTf.getBomNo()==null){
                    this.insert(dto);
                    result.setAll(20000,null,"操作成功");
                }else{
                    result.setAll(10001,null,"生产计划单已经存在，不能新增!");
                }
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn editBomMf(JmBomTfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getBomNo()) && dto.getItm()!=null ){
            //获取原先的人员属性值
            QueryWrapper<JmBomTfEntity> bomTfQueryWrapper = new QueryWrapper<>();
            bomTfQueryWrapper.eq("bom_no",dto.getBomNo());
            bomTfQueryWrapper.eq("itm",dto.getItm());
            JmBomTfDTO bomTf = this.selectOne(bomTfQueryWrapper);
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
    public CommonReturn delBomMf(List<String> bomNos, List<Integer> itms) {
        return null;
    }

    @Override
    public CommonReturn getBomMfPage(JmBomTfDTO dto, QueryWrapper queryWrapper) {
        return null;
    }
}
