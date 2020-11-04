package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmMtddMfDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.JmDevMtidEntity;
import com.BSMES.jd.main.entity.JmMtddMfEntity;
import com.BSMES.jd.main.entity.JmMtdd2TfEntity;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.main.service.JmDevMtidService;
import com.BSMES.jd.main.service.JmMtdd2TfService;
import com.BSMES.jd.main.service.JmMtddMfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JmMtddMfServiceImpl extends BaseServiceImpl<JmMtddMfDao , JmMtddMfEntity , JmMtddMfDTO> implements JmMtddMfService {

    @Autowired
    InssysvarService inssysvarService;

    @Autowired
    JmMtdd2TfService jmMtdd2TfService;

    @Autowired
    JmDevMtidService jmDevMtidService;

    @Override
    public void beforeInsert(JmMtddMfDTO dto) {
        dto.setHpdate(new Date());
    }

    @Override
    public void beforEedit(JmMtddMfDTO dto) {

    }

    @Override
    public CommonReturn getMtdd(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmMtddMfDTO> mtddMfDTOS = this.select(this.getQueryWrapper(dto));
        if(mtddMfDTOS.isEmpty()){
            result.setAll(20000,null,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,mtddMfDTOS,"查找成功");
        }
        return result;
    }

    /**
     * 老板专用接口
     * @param dto
     * @return
     */
    @Override
    public CommonReturn getMtddPlus(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmMtdd> jmMtdds = new ArrayList<>();
        //查询主表信息
        List<JmMtddMfDTO> mtddMfDTOS = this.select(this.getQueryWrapper(dto));
        /**
         * 判断两种情况
         * 1、模板表里有数据，但是子表里没有数据 （主表直接空）
         * 2、模板表里有数据，子表也有数据  (给子表信息)
         */
        if (mtddMfDTOS.size()>0){
            for (JmMtddMfDTO mtddMfDTO : mtddMfDTOS) {
                JmMtdd jmMtdd = new JmMtdd();
                //查询子表数据
                QueryWrapper<JmMtdd2TfEntity> jmMtdd2TfEntityQueryWrapper = new QueryWrapper<>();
                jmMtdd2TfEntityQueryWrapper.eq("sid", mtddMfDTO.getSid());
                List<JmMtdd2TfDTO> jmMtdd2TfDTOS = jmMtdd2TfService.select(jmMtdd2TfEntityQueryWrapper);
                jmMtdd.setJmMtddMfDTO(mtddMfDTO);
                jmMtdd.setJmMtdd2TfDTOS(jmMtdd2TfDTOS);
                jmMtdds.add(jmMtdd);
            }
        }else{
            JmMtdd jmMtdd = new JmMtdd();
            //查询模板表里的数据
            QueryWrapper<JmDevMtidEntity> jmDevMtidEntityQueryWrapper = new QueryWrapper<>();
            jmDevMtidEntityQueryWrapper.eq("devid",dto.getDevid());
            List<JmDevMtidDTO> jmDevMtidDTOS = jmDevMtidService.select(jmDevMtidEntityQueryWrapper);
            //转换数据格式
            List<JmMtdd2TfDTO> jmMtdd2TfDTOS = new ArrayList<>();
            for (int i = 0 ; i < jmDevMtidDTOS.size() ;i++){
                JmMtdd2TfDTO jmMtdd2TfDTO = new JmMtdd2TfDTO();
                jmMtdd2TfDTO.setCid(i+1);
                jmMtdd2TfDTO.setMtId(jmDevMtidDTOS.get(i).getMtId());
                jmMtdd2TfDTO.setMtName(jmDevMtidDTOS.get(i).getMtName());
                jmMtdd2TfDTO.setMtRem(jmDevMtidDTOS.get(i).getMtRem());
                jmMtdd2TfDTO.setChkStd("1");
                jmMtdd2TfDTOS.add(jmMtdd2TfDTO);
            }
            jmMtdd.setJmMtddMfDTO(new JmMtddMfDTO());
            jmMtdd.setJmMtdd2TfDTOS(jmMtdd2TfDTOS);
            jmMtdds.add(jmMtdd);
        }
        result.setAll(20000,jmMtdds,"操作成功");
        return result;
    }

//    @Override
//    public CommonReturn getMtddReport(JmMtddMfDTO dto) {
//        CommonReturn result = new CommonReturn();
//        List<JmMtdd> jmMtdds = new ArrayList<>();
//        Map<String,Object> data = MyUtils.objectToMap(dto,true);
//        List<JmMtddMfDTO> mtddMfDTOS = this.select(data);
//        if (dto!=null && dto.getJbNo()!=null){
//            for (JmMtddMfDTO mtddMfDTO : mtddMfDTOS){
//                JmMtdd jmMtdd = new JmMtdd();
//                QueryWrapper<JmMtdd2TfEntity> jmMtstd2TfEntityQueryWrapper = new QueryWrapper<>();
//                jmMtstd2TfEntityQueryWrapper.eq("sid",mtddMfDTO.getSid());
//                List<JmMtdd2TfDTO> jmMtdd2TfDTO = jmMtdd2TfService.select(jmMtstd2TfEntityQueryWrapper);
//                jmMtdd.setJmMtdd2TfDTOS(jmMtdd2TfDTO);
//                jmMtdd.setJmMtddMfDTO(mtddMfDTO);
//                jmMtdds.add(jmMtdd);
//            }
//        }
//        result.setAll(20000,mtddMfDTOS,"操作成功");
//
//        return result;
//    }

    @Transactional
    @Override
    public CommonReturn saveMtdd(JmMtdd dto) {
        CommonReturn result = new CommonReturn();
        Boolean flag = true;
        String sid = null;
        //判断是否为新增
        if (dto.getJmMtddMfDTO()!=null && dto.getJmMtddMfDTO().getSid()!=null){
            flag=false;
            sid = dto.getJmMtddMfDTO().getSid();
            this.editMtdd(dto.getJmMtddMfDTO());
        }else{
            sid = this.getKey("Mtdd","sid",inssysvarService,dto.getJmMtddMfDTO());
            dto.getJmMtddMfDTO().setSid(sid);
            this.insert(dto.getJmMtddMfDTO());
        }
        //判断是不是编辑
        try{
            if (flag==false){
                QueryWrapper<JmMtdd2TfEntity> jmMtdd2TfEntityQueryWrapper = new QueryWrapper<>();
                jmMtdd2TfEntityQueryWrapper.eq("sid",sid);
                jmMtdd2TfService.remove(jmMtdd2TfEntityQueryWrapper);
            }
            for (int i = 0 ; i < dto.getJmMtdd2TfDTOS().size() ;i++){
                dto.getJmMtdd2TfDTOS().get(i).setSid(sid);
            }
            jmMtdd2TfService.saveMtdd2s(dto.getJmMtdd2TfDTOS());
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CommonReturn editMtdd(JmMtddMfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            //获取原先的人员属性值
            QueryWrapper<JmMtddMfEntity> jmMtddMfEntityQueryWrapper = new QueryWrapper<>();
            jmMtddMfEntityQueryWrapper.eq("sid",dto.getSid());
            JmMtddMfDTO jmMtddMfDTO = this.selectOne(jmMtddMfEntityQueryWrapper);
            //设置用户不能操作的属性
            try{
                this.edit(dto);
                result.setAll(20000,null,"操作成功");
            }catch (Exception e){
                result.setAll(10001,null,"操作失败");
                e.printStackTrace();
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn delMtdd(List<String> sids) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmMtddMfEntity> jmMtddMfEntityQueryWrapper = new QueryWrapper<>();
        QueryWrapper<JmMtdd2TfEntity> jmMtdd2TfEntityQueryWrapper = new QueryWrapper<>();
        jmMtddMfEntityQueryWrapper.in("sid",sids);
        jmMtdd2TfEntityQueryWrapper.in("sid",sids);
        try{
            this.remove(jmMtddMfEntityQueryWrapper);
            jmMtdd2TfService.remove(jmMtdd2TfEntityQueryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CommonReturn getMtddPage(ResultType dto) {
        CommonReturn result = new CommonReturn();

        if (dto.getPage()==null){
            dto.setPage(1);
        }
        if (dto.getPageSize()==null){
            dto.setPageSize(10);
        }

        IPage<JmMtdd> jmMtddIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        if (jmMtddIPage==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmMtddIPage,"查找成功");
        }

        return result;
    }

    /**
     * 筛选条件
     * @param dto
     * @return
     */
    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();
        if (MyUtils.StringIsNull(dto.getSid())){
            queryWrapper.like("sid",dto.getSid());
        }else if(MyUtils.StringIsNull(dto.getType()) && dto.getSid()==null){
            queryWrapper.eq("sid",dto.getType());
        }
        if (MyUtils.StringIsNull(dto.getDevid())){
            queryWrapper.like("devid",dto.getDevid());
        }
        if (MyUtils.StringIsNull(dto.getOtherId())){
            queryWrapper.like("jb_no",dto.getOtherId());
        }
        if (dto.getBegDd()!=null){
            queryWrapper.ge("hpdate",dto.getBegDd());
        }
        if (dto.getEndDd()!=null){
            queryWrapper.le("hpdate",dto.getEndDd());
        }
        if (MyUtils.StringIsNull(dto.getDevNo())){
            queryWrapper.like("dev_no",dto.getDevNo());
        }else if (MyUtils.StringIsNull(dto.getOtherType()) && dto.getDevNo()==null){
            queryWrapper.eq("dev_no",dto.getOtherType());
        }
        if (dto.getState()!=null){
            queryWrapper.eq("state",dto.getState());
        }
        if (dto.getDevName()!=null){
            queryWrapper.like("dev_name",dto.getDevName());
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
