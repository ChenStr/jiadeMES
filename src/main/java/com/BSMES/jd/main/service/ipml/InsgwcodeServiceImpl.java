package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.InsgwcodeDao;
import com.BSMES.jd.main.dto.InsgwcodeDTO;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.InsgwcodeEntity;
import com.BSMES.jd.main.entity.InsorgEntity;
import com.BSMES.jd.main.service.InsgwcodeService;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsgwcodeServiceImpl extends BaseServiceImpl<InsgwcodeDao, InsgwcodeEntity, InsgwcodeDTO> implements InsgwcodeService {

    @Autowired
    InssysvarService inssysvarService;

    @Override
    public void beforeInsert(InsgwcodeDTO dto) {

    }

    @Override
    public void beforEedit(InsgwcodeDTO dto) {

    }

    @Override
    public CommonReturn getInsgwcode(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<InsgwcodeDTO> insgwcodeDTOS = this.select(this.getQueryWrapper(dto));
        if(insgwcodeDTOS.isEmpty()){
            result.setAll(20000,insgwcodeDTOS,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,insgwcodeDTOS,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveInsgwcode(InsgwcodeDTO dto) {
        CommonReturn result = new CommonReturn();
        if (dto.getGwcode()==null){
            dto.setGwcode(getKey("User","gwcode",inssysvarService,dto));
        }
        //判断dto是否为空 判断dto的usrcode是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getGwcode())){
            QueryWrapper<InsgwcodeEntity> insgwcodeEntityQueryWrapper = new QueryWrapper<>();
            insgwcodeEntityQueryWrapper.eq("gwcode",dto.getGwcode());
            InsgwcodeDTO sorg = this.selectOne(insgwcodeEntityQueryWrapper);
            //判断 usrcode 是否重复
            if (sorg==null || sorg.getGwcode()==null){
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
    public CommonReturn editInsgwcode(InsgwcodeDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getGwcode())){
            //获取原先的用户属性值
            QueryWrapper<InsgwcodeEntity> insgwcodeEntityQueryWrapper = new QueryWrapper<>();
            insgwcodeEntityQueryWrapper.eq("gwcode",dto.getGwcode());
            InsgwcodeDTO var = this.selectOne(insgwcodeEntityQueryWrapper);
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
    public CommonReturn delInsgwcode(List<String> gwcodes) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<InsgwcodeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("gwcode",gwcodes);
        try{
            this.remove(queryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getInsgwcodePage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<InsgwcodeDTO> insgwcodeDTOIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        if (insgwcodeDTOIPage==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,insgwcodeDTOIPage,"查找成功");
        }
        return result;
    }

    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();

        if (MyUtils.StringIsNull(dto.getSid())){
            queryWrapper.like("gwcode",dto.getSid());
        }
        if (MyUtils.StringIsNull(dto.getOtherId())){
            queryWrapper.like("gwname",dto.getOtherId());
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
