package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmMtRecDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.*;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.main.service.JmMtRecBService;
import com.BSMES.jd.main.service.JmMtRecService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class JmMtRecServiceImpl extends BaseServiceImpl<JmMtRecDao, JmMtRecEntity, JmMtRecDTO> implements JmMtRecService {

    @Autowired
    InssysvarService inssysvarService;

    @Autowired
    JmMtRecBService jmMtRecBService;


    @Override
    public void beforeInsert(JmMtRecDTO dto) {

    }

    @Override
    public void beforEedit(JmMtRecDTO dto) {

    }

    @Override
    public CommonReturn getMtRec(ResultType dto) {

        CommonReturn result = new CommonReturn();
        List<JmMtRecDTO> mts = this.select(getQueryWrapper(dto));
        if(mts.isEmpty()){
            result.setAll(20000,mts,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,mts,"查找成功");
        }
        return result;
    }

    @Transactional
    @Override
    public CommonReturn saveMtRec(JmMtRec dto) {
        CommonReturn result = new CommonReturn();
        Boolean flag = true;
        String sid = null;

        if (dto.getJmMtRecDTO()!=null && dto.getJmMtRecDTO().getWxNo()!=null){
            flag=false;
            sid = dto.getJmMtRecDTO().getWxNo();
            this.editMtRec(dto.getJmMtRecDTO());
        }else{
            sid = this.getKey("Mt","wx_no",inssysvarService,dto.getJmMtRecDTO());
            dto.getJmMtRecDTO().setWxNo(sid);
            this.insert(dto.getJmMtRecDTO());
        }

        try{
            //如果是编辑的话
            if (flag==false){
                //删除所有原始数据
                QueryWrapper<JmMtRecBEntity> jmMtRecBEntityQueryWrapper = new QueryWrapper<>();
                jmMtRecBEntityQueryWrapper.eq("wk_no",sid);
                jmMtRecBService.remove(jmMtRecBEntityQueryWrapper);
            }
            //将sid补全
            for (JmMtRecBDTO jmMtRecBDTO : dto.getJmMtRecBDTOS()){
                jmMtRecBDTO.setWxNo(sid);
            }
            //将新的数据添加进去
            jmMtRecBService.saveMtRecBs(dto.getJmMtRecBDTOS());

            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CommonReturn editMtRec(JmMtRecDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getMtId())){
            //获取原先的人员属性值
            QueryWrapper<JmMtRecEntity> mtRecQueryWrapper = new QueryWrapper<>();
            mtRecQueryWrapper.eq("wx_no",dto.getWxNo());
            JmMtRecDTO mt = this.selectOne(mtRecQueryWrapper);
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
    public CommonReturn delMtRec(List<String> wxNos) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmMtRecEntity> mtRecQueryWrapper = new QueryWrapper<>();
        mtRecQueryWrapper.in("wx_no",wxNos);
        try{
            this.remove(mtRecQueryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }


    @Override
    public CommonReturn getMtRecPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<JmDevSalDTO> jmDevSalDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),getQueryWrapper(dto));
        if (jmDevSalDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmDevSalDTOS,"查找成功");
        }
        return result;
    }

    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();

        if(dto.getAscOrder()==null && dto.getDescOrder()==null){
            dto.setDescOrder("hpdate");
        }
        if (MyUtils.StringIsNull(dto.getDevNo())){
            queryWrapper.like("dev_no",dto.getDevNo());
        }
        if (MyUtils.StringIsNull(dto.getWkNo())){
            queryWrapper.eq("sal_no",dto.getWkNo());
        }
        if (MyUtils.StringIsNull(dto.getSorg())){
            queryWrapper.eq("dep",dto.getSorg());
        }
        if (MyUtils.StringIsNull(dto.getDep())){
            queryWrapper.like("sorg",dto.getDep());
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
        if (dto.getDescOrder()!=null){
            queryWrapper.orderByDesc(MyUtils.humpToLine((String) dto.getDescOrder()));
        }

        return queryWrapper;
    }
}
