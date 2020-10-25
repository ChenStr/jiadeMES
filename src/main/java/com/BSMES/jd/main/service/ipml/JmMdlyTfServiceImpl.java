package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmMdlyTfDao;
import com.BSMES.jd.main.dto.JmChkstdTfDTO;
import com.BSMES.jd.main.dto.JmMdlyTfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmChkstdTfEntity;
import com.BSMES.jd.main.entity.JmMdlyTfEntity;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.main.service.JmMdlyTfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JmMdlyTfServiceImpl extends BaseServiceImpl<JmMdlyTfDao, JmMdlyTfEntity, JmMdlyTfDTO> implements JmMdlyTfService {

    @Autowired
    InssysvarService inssysvarService;

    @Autowired
    JmMdlyTfDao jmMdlyTfDao;

    @Override
    public void beforeInsert(JmMdlyTfDTO dto) {

    }

    @Override
    public void beforEedit(JmMdlyTfDTO dto) {

    }

    @Override
    public CommonReturn getMdlyTf(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmMdlyTfDTO> dtos = this.select(this.getQueryWrapper(dto));
        if(dtos.isEmpty()){
            result.setAll(20000,dtos,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,dtos,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveMdlyTf(JmMdlyTfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的usrcode是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            QueryWrapper<JmMdlyTfEntity> jmMdlyTfEntityQueryWrapper = new QueryWrapper<>();
            jmMdlyTfEntityQueryWrapper.eq("sid",dto.getSid()).eq("cid",dto.getCid());
            JmMdlyTfDTO jmMdlyTfDTO = this.selectOne(jmMdlyTfEntityQueryWrapper);
            //判断 usrcode 是否重复
            if (jmMdlyTfDTO==null || jmMdlyTfDTO.getSid()==null){
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
    public CommonReturn saveMdlyTfs(List<JmMdlyTfDTO> dtos) {
        CommonReturn result = new CommonReturn();
        try{
            jmMdlyTfDao.insertMdlys(dtos);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CommonReturn editMdlyTf(JmMdlyTfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            //获取原先的用户属性值
            QueryWrapper<JmMdlyTfEntity> jmMdlyTfEntityQueryWrapper = new QueryWrapper<>();
            jmMdlyTfEntityQueryWrapper.eq("sid",dto.getSid());
            JmMdlyTfDTO jmMdlyTfDTO = this.selectOne(jmMdlyTfEntityQueryWrapper);
            //设置用户不能操作的属性
            try{
//                this.edit(dto);
                jmMdlyTfDao.editMdly(dto);
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
    public CommonReturn delMdlyTf(List<String> sids, List<Integer> cids) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmMdlyTfEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("sid",sids);
        queryWrapper.in("cid",cids);
        try{
            this.remove(queryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getMdlyTfPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<JmMdlyTfDTO> jmMdlyTfDTOIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        if (jmMdlyTfDTOIPage==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmMdlyTfDTOIPage,"查找成功");
        }
        return result;
    }

    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();

//        //默认排序
//        if(dto.getAscOrder()==null && dto.getDescOrder()==null){
//            dto.setDescOrder("sort");
//        }
//
//        //筛选
//        if (MyUtils.StringIsNull(dto.getSid())){
//            queryWrapper.like("orgcode",dto.getSid());
//        }
//
//        //排序
//        if (dto.getAscOrder()!=null){
//            queryWrapper.orderByAsc(MyUtils.humpToLine((String) dto.getAscOrder()));
//        }
//        if (dto.getDescOrder()!=null && dto.getAscOrder()==null){
//            queryWrapper.orderByDesc(MyUtils.humpToLine((String) dto.getDescOrder()));
//        }


        return queryWrapper;
    }


}
