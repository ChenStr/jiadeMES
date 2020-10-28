package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmBsDictionaryDao;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.JmBsDictionaryDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.InsorgEntity;
import com.BSMES.jd.main.entity.JmBsDictionaryEntity;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.main.service.JmBsDictionaryService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JmBsDictionaryServiceImpl extends BaseServiceImpl<JmBsDictionaryDao , JmBsDictionaryEntity , JmBsDictionaryDTO> implements JmBsDictionaryService {

    @Autowired
    InssysvarService inssysvarService;

    @Override
    public void beforeInsert(JmBsDictionaryDTO dto) {
        dto.setState(1);
        dto.setIsDel("0");
    }

    @Override
    public void beforEedit(JmBsDictionaryDTO dto) {

    }

    @Override
    public CommonReturn getDictionary(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmBsDictionaryDTO> jmBsDictionaryDTOS = this.select(this.getQueryWrapper(dto));
        if(jmBsDictionaryDTOS.isEmpty()){
            result.setAll(20000,jmBsDictionaryDTOS,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,jmBsDictionaryDTOS,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveDictionary(JmBsDictionaryDTO dto) {
        CommonReturn result = new CommonReturn();
        if (dto.getId()==null){
            dto.setId(getKey("Dis","id",inssysvarService,dto));
        }
        //判断dto是否为空 判断dto的usrcode是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getId())){
            QueryWrapper<JmBsDictionaryEntity> jmBsDictionaryEntityQueryWrapper = new QueryWrapper<>();
            jmBsDictionaryEntityQueryWrapper.eq("id",dto.getId());
            JmBsDictionaryDTO dis = this.selectOne(jmBsDictionaryEntityQueryWrapper);
            //判断 usrcode 是否重复
            if (dis==null || dis.getCode()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"字典号已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn editDictionary(JmBsDictionaryDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getCode())){
            //获取原先的用户属性值
            QueryWrapper<JmBsDictionaryEntity> jmBsDictionaryEntityQueryWrapper = new QueryWrapper<>();
            jmBsDictionaryEntityQueryWrapper.eq("id",dto.getId());
            JmBsDictionaryDTO var = this.selectOne(jmBsDictionaryEntityQueryWrapper);
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
    public CommonReturn delfalseDictionary(JmBsDictionaryDTO dto) {
        CommonReturn result = new CommonReturn();
        if(dto!=null && dto.getId()!=null){
            //判断他是父亲还是孩子
            QueryWrapper<JmBsDictionaryEntity> jmBsDictionaryEntityQueryWrapper = new QueryWrapper<>();
            jmBsDictionaryEntityQueryWrapper.eq("id",dto.getId()).or().eq("pid",dto.getId());
            JmBsDictionaryEntity jmBsDictionaryEntity = new JmBsDictionaryEntity();
            jmBsDictionaryEntity.setIsDel("1");
            update(jmBsDictionaryEntity,jmBsDictionaryEntityQueryWrapper);
            result.setAll(20000,null,"操作成功");
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn delDictionary(List<String> ids) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmBsDictionaryEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);
        try{
            this.remove(queryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getDictionaryPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<JmBsDictionaryDTO> jmBsDictionaryDTOIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        if (jmBsDictionaryDTOIPage==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmBsDictionaryDTOIPage,"查找成功");
        }
        return result;
    }

    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();

        if(dto.getAscOrder()==null && dto.getDescOrder()==null){
            dto.setDescOrder("sort");
        }

        if (MyUtils.StringIsNull(dto.getSid())){
            queryWrapper.like("id",dto.getSid());
        }
        if (MyUtils.StringIsNull(dto.getOtherId())){
            queryWrapper.like("pid",dto.getOtherId());
        }
        if (dto.getState()!=null){
            queryWrapper.eq("state",dto.getState());
        }
        if (dto.getState()!=null){
            queryWrapper.eq("state",dto.getState());
        }
        if (MyUtils.StringIsNull(dto.getType())){
            queryWrapper.like("is_del",dto.getType());
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
