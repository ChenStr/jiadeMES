package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmMdbfTfDao;
import com.BSMES.jd.main.dto.JmMdbfMfDTO;
import com.BSMES.jd.main.dto.JmMdbfTfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmMdbfMfEntity;
import com.BSMES.jd.main.entity.JmMdbfTfEntity;
import com.BSMES.jd.main.service.JmMdbfTfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JmMdbfTfServiceImpl extends BaseServiceImpl<JmMdbfTfDao, JmMdbfTfEntity, JmMdbfTfDTO> implements JmMdbfTfService {

    @Autowired
    JmMdbfTfDao jmMdbfTfDao;

    @Override
    public void beforeInsert(JmMdbfTfDTO dto) {

    }

    @Override
    public void beforEedit(JmMdbfTfDTO dto) {

    }

    @Override
    public CommonReturn getMdbfTf(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmMdbfTfDTO> jmMdbfTfDTOS = this.select(this.getQueryWrapper(dto));
        if(jmMdbfTfDTOS.isEmpty()){
            result.setAll(20000,jmMdbfTfDTOS,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,jmMdbfTfDTOS,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveMdbfTfs(List<JmMdbfTfDTO> dtos) {
        CommonReturn result = new CommonReturn();
        try{
            jmMdbfTfDao.saveMdbf(dtos);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CommonReturn editMdbfTf(JmMdbfTfDTO dto) {
        CommonReturn result = new CommonReturn();
        try{
            jmMdbfTfDao.editMdbf(dto);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn delMdbfTf(List<String> sids) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmMdbfTfEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("sid",sids);
        try{
            this.remove(queryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getMdbfTfPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<JmMdbfTfDTO> jmMdbfTfDTOIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        if (jmMdbfTfDTOIPage==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmMdbfTfDTOIPage,"查找成功");
        }
        return result;
    }

    /**
     * 填写筛选数据
     * @param dto
     * @return
     */
    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();

        if (MyUtils.StringIsNull(dto.getSid())){
            queryWrapper.eq("sid",dto.getSid());
        }
        if (MyUtils.StringIsNull(dto.getMouldNo())){
            queryWrapper.like("md_no",dto.getMouldNo());
        }
        if (MyUtils.StringIsNull(dto.getMouldName())){
            queryWrapper.like("md_name",dto.getMouldName());
        }
        if (MyUtils.StringIsNull(dto.getType())){
            queryWrapper.eq("typeid",dto.getType());
        }
        if (dto.getState()!=null){
            queryWrapper.eq("ed_locked",dto.getState());
        }
        if (dto.getBegDd()!=null){
            queryWrapper.ge("hpdate",dto.getBegDd());
        }
        if(dto.getEndDd()!=null){
            queryWrapper.le("hpdate",dto.getEndDd());
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
