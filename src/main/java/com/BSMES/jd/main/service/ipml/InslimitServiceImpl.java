package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.InslimitDao;
import com.BSMES.jd.main.dto.Inslimit;
import com.BSMES.jd.main.dto.InslimitDTO;
import com.BSMES.jd.main.dto.JmMouldDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.InslimitEntity;
import com.BSMES.jd.main.entity.JmMouldEntity;
import com.BSMES.jd.main.service.InslimitService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InslimitServiceImpl extends BaseServiceImpl<InslimitDao , InslimitEntity , InslimitDTO> implements InslimitService {

    @Autowired
    InslimitDao inslimitDao;

    @Override
    public void beforeInsert(InslimitDTO dto) {

    }

    @Override
    public void beforEedit(InslimitDTO dto) {

    }

    @DS("master")
    @Override
    public CommonReturn getInslimit(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<InslimitDTO> moulds = this.select(this.getQueryWrapper(dto));
        if(moulds.isEmpty()){
            result.setAll(20000,moulds,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,moulds,"查找成功");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn getInsLimit(ResultType dto) {
        CommonReturn result = new CommonReturn();
        try {
            List<Inslimit> inslimits = inslimitDao.getInslimit(dto);
            result.setAll(20000,inslimits,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn saveInslimit(InslimitDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 md_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getGwuser()) && dto.getMenuid()!=null ){
            QueryWrapper<InslimitEntity> inslimitEntityQueryWrapper = new QueryWrapper<>();
            inslimitEntityQueryWrapper.eq("gwuser",dto.getGwuser());
            inslimitEntityQueryWrapper.eq("menuid",dto.getMenuid());
            InslimitDTO inslimitDTO = this.selectOne(inslimitEntityQueryWrapper);
            //判断 usrcode 是否重复
            if (inslimitDTO==null || inslimitDTO.getGwuser()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"模具号已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn editInslimit(InslimitDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 md_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getGwuser()) && dto.getMenuid()!=null ){
            //获取原先的人员属性值
            QueryWrapper<InslimitEntity> inslimitEntityQueryWrapper = new QueryWrapper<>();
            inslimitEntityQueryWrapper.eq("gwuser",dto.getGwuser());
            inslimitEntityQueryWrapper.eq("menuid",dto.getMenuid());
            InslimitDTO inslimitDTO = this.selectOne(inslimitEntityQueryWrapper);
            //设置用户不能操作的属性
            try{
                this.edit(dto);
//                jmMouldDao.editMould(dto);
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
    public CommonReturn delInslimit(List<String> gwusers, List<String> menuids) {
        CommonReturn result = new CommonReturn();
        //判断长度是否相等
        if (gwusers!=null && menuids!=null && gwusers.size()!=0 && menuids.size()!=0 && gwusers.size()==menuids.size()){
            for (int i = 0 ; i < gwusers.size() ; i++){
                QueryWrapper<InslimitEntity> inslimitEntityQueryWrapper = new QueryWrapper<>();
                inslimitEntityQueryWrapper.eq("gwuser",gwusers.get(i));
                inslimitEntityQueryWrapper.eq("menuid",menuids.get(i));
                try{
                    this.remove(inslimitEntityQueryWrapper);
                    result.setAll(20000,null,"操作成功");
                }catch (Exception e) {
                    result.setAll(10001, null, "操作失败");
                    return result;
                }
            }

        }else{
            result.setAll(10001,null,"操作失败");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn getInslimitPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<InslimitDTO> inslimitDTOIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        if (inslimitDTOIPage==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,inslimitDTOIPage,"查找成功");
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

        if (MyUtils.StringIsNull(dto.getWkNo())){
            queryWrapper.eq("gwuser",dto.getWkNo());
        }
        if (MyUtils.StringIsNull(dto.getOtherId())){
            queryWrapper.like("menuid",dto.getOtherId());
        }
        if (MyUtils.StringIsNull(dto.getWkName())){
            queryWrapper.like("cgw",dto.getWkName());
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
