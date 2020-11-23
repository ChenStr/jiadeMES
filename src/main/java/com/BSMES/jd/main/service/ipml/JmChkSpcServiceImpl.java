package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmChkSpcDao;
import com.BSMES.jd.main.dto.InsgwcodeDTO;
import com.BSMES.jd.main.dto.JmChkSpcDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.InsgwcodeEntity;
import com.BSMES.jd.main.entity.JmChkSpcEntity;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.main.service.JmChkSpcService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JmChkSpcServiceImpl extends BaseServiceImpl<JmChkSpcDao, JmChkSpcEntity, JmChkSpcDTO> implements JmChkSpcService {

    @Autowired
    InssysvarService inssysvarService;

    @Override
    public void beforeInsert(JmChkSpcDTO dto) {
        dto.setHpdate(new Date());
    }

    @Override
    public void beforEedit(JmChkSpcDTO dto) {

    }

    @Override
    public CommonReturn getJmChkSpc(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmChkSpcDTO> jmChkSpcDTOS = this.select(this.getQueryWrapper(dto));
        if(jmChkSpcDTOS.isEmpty()){
            result.setAll(20000,jmChkSpcDTOS,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,jmChkSpcDTOS,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveJmChkSpc(JmChkSpcDTO dto) {
        CommonReturn result = new CommonReturn();
        if (dto.getSpcChk()==null){
            dto.setSpcChk(getKey("User","spc_chk",inssysvarService,dto));
        }
        //判断dto是否为空 判断dto的usrcode是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSpcChk())){
            QueryWrapper<JmChkSpcEntity> jmChkSpcEntityQueryWrapper = new QueryWrapper<>();
            jmChkSpcEntityQueryWrapper.eq("spc_chk",dto.getSpcChk());
            JmChkSpcDTO jmChkSpcDTO = this.selectOne(jmChkSpcEntityQueryWrapper);
            //判断 usrcode 是否重复
            if (jmChkSpcDTO==null || jmChkSpcDTO.getSpcChk()==null){
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
    public CommonReturn editJmChkSpc(JmChkSpcDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSpcChk())){
            //获取原先的用户属性值
            QueryWrapper<JmChkSpcEntity> jmChkSpcEntityQueryWrapper = new QueryWrapper<>();
            jmChkSpcEntityQueryWrapper.eq("spc_chk",dto.getSpcChk());
            JmChkSpcDTO var = this.selectOne(jmChkSpcEntityQueryWrapper);
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
    public CommonReturn delJmChkSpc(List<String> spcChks) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmChkSpcEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("spc_chk",spcChks);
        try{
            this.remove(queryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getJmChkSpcPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<JmChkSpcDTO> jmChkSpcDTOIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        if (jmChkSpcDTOIPage==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmChkSpcDTOIPage,"查找成功");
        }
        return result;
    }

    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();

        if (dto.getBegDd()!=null){
            queryWrapper.ge("hpdate",dto.getBegDd());
        }
        if(dto.getEndDd()!=null){
            queryWrapper.le("hpdate",dto.getEndDd());
        }
        if (MyUtils.StringIsNull(dto.getSid())){
            queryWrapper.eq("spc_chk",dto.getSid());
        }
        if (MyUtils.StringIsNull(dto.getSorg())){
            queryWrapper.eq("zc_no",dto.getSorg());
        }
        if (MyUtils.StringIsNull(dto.getWkName())){
            queryWrapper.eq("smake",dto.getWkName());
        }
        if (MyUtils.StringIsNull(dto.getType())){
            queryWrapper.eq("spc_type",dto.getType());
        }
        if (MyUtils.StringIsNull(dto.getPrdNo())){
            queryWrapper.eq("prd_no",dto.getPrdNo());
        }
        if (MyUtils.StringIsNull(dto.getOtherId())){
            queryWrapper.like("name",dto.getOtherId());
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
