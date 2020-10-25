package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmMtstdMfDao;
import com.BSMES.jd.main.dao.JmMtstdTfDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.*;
import com.BSMES.jd.main.service.*;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class JmMtstdMfServiceImpl extends BaseServiceImpl<JmMtstdMfDao , JmMtstdMfEntity , JmMtstdMfDTO> implements JmMtstdMfService {

    @Autowired
    InssysvarService inssysvarService;

    @Autowired
    JmMtstdMfDao jmMtstdMfDao;

    @Autowired
    JmMtstdTfDao jmMtstdTfDao;

    @Autowired
    JmDevService jmDevService;

    @Autowired
    JmMtstdTfService jmMtstdTfService;

    @Autowired
    JmDevMtidService jmDevMtidService;

    @Override
    public void beforeInsert(JmMtstdMfDTO dto) {

    }

    @Override
    public void beforEedit(JmMtstdMfDTO dto) {

    }

    @Override
    public CommonReturn getMtstdMf(JmMtstdMfDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto,true);
        List<JmMtstdMfDTO> mtstdMf = this.select(data);
        if(mtstdMf.isEmpty()){
            result.setAll(20000,mtstdMf,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,mtstdMf,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn getMtstdMfPlus(ResultType dto) {
        CommonReturn result = new CommonReturn();
        try{
            List<JmMtstd> jmMtstds = jmMtstdMfDao.getJmMtstd(dto);
            //将对应的设备分类的设备维修项目放入
            for (JmMtstd jmMtstd : jmMtstds){
                QueryWrapper<JmDevMtidEntity> jmDevMtidEntityQueryWrapper = new QueryWrapper<>();
                jmDevMtidEntityQueryWrapper.eq("devid",jmMtstd.getJmDevDTO().getDevid());
                List<JmDevMtidDTO> jmDevMtidDTOS = jmDevMtidService.select(jmDevMtidEntityQueryWrapper);
                if (jmDevMtidDTOS!=null && jmDevMtidDTOS.size()>0){
                    jmMtstd.setJmDevMtidDTOS(jmDevMtidDTOS);
                }
            }

            result.setAll(20000,jmMtstds,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }

        return result;
    }

    @Transactional
    @Override
    public CommonReturn saveMtstdMfPlus(JmMtstd dto) {
        CommonReturn result = new CommonReturn();
        Boolean flag = true;
        String sid = null;

        if (dto.getJmMtstdMfDTO()!=null && dto.getJmMtstdMfDTO().getMtstdNo()!=null){
            flag = false;
            sid = dto.getJmMtstdMfDTO().getMtstdNo();
            this.editMtstdMf(dto.getJmMtstdMfDTO());
        }else{
            sid = this.getKey("JmMtstdMf","mtstd_no",inssysvarService,dto.getJmMtstdMfDTO());
            dto.getJmMtstdMfDTO().setMtstdNo(sid);
            this.insert(dto.getJmMtstdMfDTO());
        }

        try{
            //如果是编辑那么要删除原始的数据
            if (flag==false){
                QueryWrapper<JmMtstdTfEntity> jmMtstdTfEntityQueryWrapper = new QueryWrapper<>();
                jmMtstdTfEntityQueryWrapper.eq("mtstd_no",dto.getJmMtstdMfDTO().getMtstdNo());
                jmMtstdTfService.remove(jmMtstdTfEntityQueryWrapper);
            }
            //新增表身数据 并给予主键
            for (JmMtstdTf jmMtstdTf : dto.getJmMtstdTfs()){
                jmMtstdTf.getJmMtstdTf().setMtstdNo(sid);
            }
            jmMtstdTfDao.SaveJmMtstdTfs(dto.getJmMtstdTfs());
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
        }

        return result;
    }

    @Override
    public CommonReturn saveMtstdMf(JmMtstdMfDTO dto) {
        CommonReturn result = new CommonReturn();
        dto.setMtstdNo(this.getKey("JmMtstdMf" , "mtstd_no" , inssysvarService , dto));
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getMtstdNo())){
            QueryWrapper<JmMtstdMfEntity> mtstdMfQueryWrapper = new QueryWrapper<>();
            mtstdMfQueryWrapper.eq("mtstd_no",dto.getMtstdNo());
            JmMtstdMfDTO mt = this.selectOne(mtstdMfQueryWrapper);
            //判断设备是否存在
            QueryWrapper<JmDevEntity> devQueryWrapper = new QueryWrapper<>();
            devQueryWrapper.eq("dev_no",dto.getDevNo());
            JmDevDTO devDTO = jmDevService.selectOne(devQueryWrapper);
            //判断 usrcode 是否重复
            if ( (devDTO!=null && devDTO.getDevNo()!=null) && (mt==null || mt.getMtstdNo()==null)){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"单号已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn editMtstdMf(JmMtstdMfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getMtstdNo())){
            //获取原先的人员属性值
            QueryWrapper<JmMtstdMfEntity> mtstdMfQueryWrapper = new QueryWrapper<>();
            mtstdMfQueryWrapper.eq("mtstd_no",dto.getMtstdNo());
            JmMtstdMfDTO mt = this.selectOne(mtstdMfQueryWrapper);
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
    public CommonReturn delMtstdMf(List<String> mtstdNos) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmMtstdMfEntity> mtstdMfQueryWrapper = new QueryWrapper<>();
        mtstdMfQueryWrapper.in("mtstd_no",mtstdNos);
        QueryWrapper<JmMtstdTfEntity> jmMtstdTfEntityQueryWrapper = new QueryWrapper<>();
        jmMtstdTfEntityQueryWrapper.eq("mtstd_no",mtstdNos);
        try{
            this.remove(mtstdMfQueryWrapper);
            jmMtstdTfService.remove(jmMtstdTfEntityQueryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getMtstdMfPage(JmMtstdMfDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        IPage<JmMtstdMfDTO> jmMtstdMfDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmMtstdMfDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmMtstdMfDTOS,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn getMtstdMfPlusPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        if (dto.getDescOrder()==null && dto.getAscOrder()==null){
            dto.setDescOrder("create_date");
        }
        if (dto.getPage()==null){
            dto.setPage(1);
        }
        if (dto.getPageSize()==null){
            dto.setPageSize(10);
        }
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        List<JmMtstd> data = (List<JmMtstd>) this.getMtstdMfPlus(dto).getData();
        for (JmMtstd jmMtstd : data){
            QueryWrapper<JmDevMtidEntity> jmDevMtidEntityQueryWrapper = new QueryWrapper<>();
            jmDevMtidEntityQueryWrapper.eq("devid",jmMtstd.getJmDevDTO().getDevid());
            List<JmDevMtidDTO> jmDevMtidDTOS = jmDevMtidService.select(jmDevMtidEntityQueryWrapper);
            if (jmDevMtidDTOS!=null && jmDevMtidDTOS.size()>0){
                jmMtstd.setJmDevMtidDTOS(jmDevMtidDTOS);
            }
        }
        PageInfo Pagedata = new PageInfo<JmMtstd>(data);
        //总页数有问题需要手动设置一下
        List<JmMtstdMfEntity> jmMtstdMfDTOS = this.list();
        Pagedata.setTotal(jmMtstdMfDTOS.size());
        result.setAll(20000,Pagedata,"操作成功");
        return result;
    }
}
