package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmDevDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.JmDevEntity;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.main.service.JmDevService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JmDevServiceImpl extends BaseServiceImpl<JmDevDao, JmDevEntity, JmDevDTO> implements JmDevService {

    @Autowired
    InssysvarService inssysvarService;

    @Autowired
    JmDevDao jmDevDao;

    @Override
    public void beforeInsert(JmDevDTO dto) {

    }

    @Override
    public void beforEedit(JmDevDTO dto) {

    }


    @DS("master")
    @Override
    public CommonReturn getDev(JmDevDTO dto) {
        CommonReturn result = new CommonReturn();
        List<JmDevDTO> devs = this.select(getQueryWrapper(dto));
        if(devs.isEmpty()){
            result.setAll(20000,devs,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,devs,"查找成功");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn saveDev(JmDevDTO dto) {
        CommonReturn result = new CommonReturn();
        if(dto.getDevNo()==null){
            dto.setDevNo(getKey("Dev","dev_no",inssysvarService,dto));
        }
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getDevNo())){
            QueryWrapper<JmDevEntity> devQueryWrapper = new QueryWrapper<>();
            devQueryWrapper.eq("dev_no",dto.getDevNo());
            JmDevDTO dev = this.selectOne(devQueryWrapper);
            //判断 usrcode 是否重复
            if (dev==null || dev.getDevNo()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"设备已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn editDev(JmDevDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getDevNo())){
            //获取原先的人员属性值
            QueryWrapper<JmDevEntity> devQueryWrapper = new QueryWrapper<>();
            devQueryWrapper.eq("dev_no",dto.getDevNo());
            JmDevDTO worker = this.selectOne(devQueryWrapper);
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

    @DS("master")
    @Override
    public CommonReturn delDev(List<String> devNos) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmDevEntity> devQueryWrapper = new QueryWrapper<>();
        devQueryWrapper.in("dev_no",devNos);
        try{
            this.remove(devQueryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn getLook(ResultType dto) {
        CommonReturn result = new CommonReturn();
        if (dto.getPage()==null){
            dto.setPage(1);
        }
        if (dto.getPageSize()==null){
            dto.setPageSize(10);
        }
        try{
            PageHelper.startPage(dto.getPage(), dto.getPageSize());
            List<Report> reports = jmDevDao.getlookbord(dto);
            PageInfo reportPageInfo = new PageInfo<Report>(reports);
            result.setAll(20000,reportPageInfo,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn getDevPage(JmDevDTO dto) {
        CommonReturn result = new CommonReturn();

        if (dto.getPage()==null){
            dto.setPage(1);
        }
        if (dto.getPageSize()==null){
            dto.setPageSize(10);
        }
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        List<JmDevDTO> dev = (List<JmDevDTO>) this.getDev(dto).getData();
        PageInfo pageInfo = new PageInfo<JmDevDTO>(dev);
        pageInfo.setTotal(((List<JmDevDTO>) this.getDev(dto).getData()).size());
        result.setAll(20000,pageInfo,"操作成功");

        return result;
    }

    /**
     * 填写筛选数据
     * @param dto
     * @return
     */
    private QueryWrapper getQueryWrapper(JmDevDTO dto){
        QueryWrapper queryWrapper = new QueryWrapper();

        if (MyUtils.StringIsNull(dto.getDevNo())){
            queryWrapper.eq("dev_no",dto.getDevNo());
        }else if(MyUtils.StringIsNull(dto.getDev_no())){
            queryWrapper.like("dev_no",dto.getDev_no());
        }
        if (MyUtils.StringIsNull(dto.getName())){
            queryWrapper.like("name",dto.getName());
        }
        if (MyUtils.StringIsNull(dto.getSpc())){
            queryWrapper.eq("spc",dto.getSpc());
        }
        if (MyUtils.StringIsNull(dto.getRsNo())){
            queryWrapper.like("rs_no",dto.getRsNo());
        }
        if (MyUtils.StringIsNull(dto.getCusname())){
            queryWrapper.eq("cusname",dto.getCusname());
        }
        if (MyUtils.StringIsNull(dto.getMainNo())){
            queryWrapper.eq("main_no",dto.getMainNo());
        }
        if (MyUtils.StringIsNull(dto.getDep())){
            queryWrapper.eq("dep",dto.getDep());
        }
        if (dto.getMaxtime()!=null){
            queryWrapper.eq("maxtime",dto.getMaxtime());
        }
        if (dto.getMaxqty()!=null){
            queryWrapper.eq("maxqty",dto.getMaxqty());
        }
        if (dto.getTimeMk()!=null){
            queryWrapper.eq("time_mk",dto.getTimeMk());
        }
        if (dto.getQtyMk()!=null){
            queryWrapper.eq("qty_mk",dto.getQtyMk());
        }
        if (MyUtils.StringIsNull(dto.getState())){
            queryWrapper.eq("state",dto.getState());
        }
        if (MyUtils.StringIsNull(dto.getDevid())){
            queryWrapper.eq("devid",dto.getDevid());
        }
        if (MyUtils.StringIsNull(dto.getSorg())){
            queryWrapper.eq("sorg",dto.getSorg());
        }
        if (MyUtils.StringIsNull(dto.getPicture())){
            queryWrapper.eq("picture",dto.getPicture());
        }
        if (MyUtils.StringIsNull(dto.getLocation())){
            queryWrapper.eq("location",dto.getLocation());
        }
        if (MyUtils.StringIsNull(dto.getIp())){
            queryWrapper.eq("ip",dto.getIp());
        }
        if (MyUtils.StringIsNull(dto.getState())){
            queryWrapper.eq("dep_name",dto.getDepName());
        }
        return queryWrapper;
    }
}
