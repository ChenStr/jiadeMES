package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmWxIdDao;
import com.BSMES.jd.main.dto.InslimitDTO;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.JmWxIdDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.InsorgEntity;
import com.BSMES.jd.main.entity.JmWxIdEntity;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.main.service.JmWxIdService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service()
public class JmWxIdServiceImpl extends BaseServiceImpl<JmWxIdDao, JmWxIdEntity, JmWxIdDTO> implements JmWxIdService {

    @Autowired
    InssysvarService inssysvarService;

    @Override
    public void beforeInsert(JmWxIdDTO dto) {

    }

    @Override
    public void beforEedit(JmWxIdDTO dto) {

    }

    @Override
    public CommonReturn getJmWxId(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<InslimitDTO> inslimitDTOS = this.select(this.getQueryWrapper(dto));
        if(inslimitDTOS.isEmpty()){
            result.setAll(20000,inslimitDTOS,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,inslimitDTOS,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveJmWxId(JmWxIdDTO dto) {
        CommonReturn result = new CommonReturn();
        if (dto.getMtId()==null){
            dto.setMtId(this.getKey("Mt","mt_id",inssysvarService,dto));
        }
        //判断dto是否为空 判断dto的usrcode是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getMtId())){
            QueryWrapper<JmWxIdEntity> jmWxIdEntityQueryWrapper = new QueryWrapper<>();
            jmWxIdEntityQueryWrapper.eq("mt_id",dto.getMtId());
            JmWxIdDTO jmWxIdDTO = this.selectOne(jmWxIdEntityQueryWrapper);
            //判断 usrcode 是否重复
            if (jmWxIdDTO==null || jmWxIdDTO.getMtId()==null){
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
    public CommonReturn editJmWxId(JmWxIdDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getMtId())){
            //获取原先的用户属性值
            QueryWrapper<JmWxIdEntity> jmWxIdEntityQueryWrapper = new QueryWrapper<>();
            jmWxIdEntityQueryWrapper.eq("mt_id",dto.getMtId());
            JmWxIdDTO var = this.selectOne(jmWxIdEntityQueryWrapper);
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
    public CommonReturn delJmWxId(List<String> mtIds) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmWxIdEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("mt_id",mtIds);
        try{
            this.remove(queryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getJmWxIdPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<JmWxIdDTO> jmWxIdDTOIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        if (jmWxIdDTOIPage==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmWxIdDTOIPage,"查找成功");
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

        if (MyUtils.StringIsNull(dto.getSorg())){
            queryWrapper.like("dep",dto.getSorg());
        }
        if (MyUtils.StringIsNull(dto.getDep())){
            queryWrapper.like("dep_name",dto.getDep());
        }
        if (MyUtils.StringIsNull(dto.getType())){
            queryWrapper.like("mdGrp",dto.getType());
        }
        if (MyUtils.StringIsNull(dto.getOtherId())){
            queryWrapper.like("name",dto.getOtherId());
        }
        if (MyUtils.StringIsNull(dto.getSid())){
            queryWrapper.eq("mt_id",dto.getSid());
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
