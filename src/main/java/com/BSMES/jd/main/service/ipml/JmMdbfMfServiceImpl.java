package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmMdbfMfDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.JmMdbfMfEntity;
import com.BSMES.jd.main.entity.JmMdbfTfEntity;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.main.service.JmMdbfMfService;
import com.BSMES.jd.main.service.JmMdbfTfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JmMdbfMfServiceImpl extends BaseServiceImpl<JmMdbfMfDao, JmMdbfMfEntity, JmMdbfMfDTO> implements JmMdbfMfService {

    @Autowired
    InssysvarService inssysvarService;

    @Autowired
    JmMdbfTfService jmMdbfTfService;

    @Override
    public void beforeInsert(JmMdbfMfDTO dto) {

    }

    @Override
    public void beforEedit(JmMdbfMfDTO dto) {

    }

    @Override
    public CommonReturn getMdbfMf(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmMdbfMfDTO> mdbfMfDTOS = this.select(this.getQueryWrapper(dto));
        if(mdbfMfDTOS.isEmpty()){
            result.setAll(20000,mdbfMfDTOS,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,mdbfMfDTOS,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveMdbf(JmMdbf dto) {
        CommonReturn result = new CommonReturn();
        Boolean flag = true;
        String sid = null;

        //首先判断是添加还是修改
//        if (dto.getJmChkstdMfDTO()!=null && dto.getJmChkstdMfDTO().getChkstdNo()!=null){
//            flag=false;
//            sid = dto.getJmChkstdMfDTO().getChkstdNo();
//            this.edit(dto.getJmChkstdMfDTO());
//        }else{
//            sid = this.getKey("JmChkstd","chkstd_no",inssysvarService,dto.getJmChkstdMfDTO());
//            dto.getJmChkstdMfDTO().setChkstdNo(sid);
//            this.insert(dto.getJmChkstdMfDTO());
//        }
//
//        try{
//
//            //如果是编辑的话先进行删除
//            if (flag==false){
//                //删除所有的原始数据
//                QueryWrapper<JmChkstdTfEntity> jmChkstdTfEntityQueryWrapper = new QueryWrapper<>();
//                jmChkstdTfEntityQueryWrapper.eq("chkstd_no",sid);
//                jmChkstdTfService.remove(jmChkstdTfEntityQueryWrapper);
//            }
//            //将新的数据新增进去
//            for(JmChkstdTfDTO jmChkstdTfDTO : dto.getJmChkstdTfDTOS()){
//                jmChkstdTfDTO.setChkstdNo(sid);
//            }
//            jmChkstdTfService.saveChkstdTfs(dto.getJmChkstdTfDTOS());
//            result.setAll(20000,null,"操作成功");
//        }catch (Exception e){
//            result.setAll(40000,null,"操作失败");
//            e.printStackTrace();
//        }
        return result;
    }

    @Override
    public CommonReturn saveMdbfMf(JmMdbfMfDTO dto) {
        CommonReturn result = new CommonReturn();
        if (dto.getSid()==null){
            dto.setSid(getKey("Mdbf","sid",inssysvarService,dto));
        }
        //判断dto是否为空 判断dto的usrcode是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            QueryWrapper<JmMdbfMfEntity> mdbfQueryWrapper = new QueryWrapper<>();
            mdbfQueryWrapper.eq("sid",dto.getSid());
            JmMdbfMfDTO mdbf = this.selectOne(mdbfQueryWrapper);
            //判断 usrcode 是否重复
            if (mdbf==null || mdbf.getSid()==null){
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
    public CommonReturn editMdbfMf(JmMdbfMfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            //获取原先的用户属性值
            QueryWrapper<JmMdbfMfEntity> sorgqueryWrapper = new QueryWrapper<>();
            sorgqueryWrapper.eq("sid",dto.getSid());
            JmMdbfMfDTO var = this.selectOne(sorgqueryWrapper);
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
    public CommonReturn delMdbfMf(List<String> sids) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmMdbfMfEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("sid",sids);
        QueryWrapper<JmMdbfTfEntity> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.in("sid",sids);
        try{
            this.remove(queryWrapper);
            jmMdbfTfService.remove(queryWrapper1);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getMdbkMfPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<JmMdbfMfDTO> jmMdbfMfDTOIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        if (jmMdbfMfDTOIPage==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmMdbfMfDTOIPage,"查找成功");
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
        if (dto.getState()!=null){
            queryWrapper.eq("state",dto.getState());
        }
        if (MyUtils.StringIsNull(dto.getType())){
            queryWrapper.eq("ed_locked",dto.getType());
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
