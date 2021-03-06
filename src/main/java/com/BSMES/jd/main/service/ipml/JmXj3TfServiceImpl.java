package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmXj2TfDao;
import com.BSMES.jd.main.dao.JmXj3TfDao;
import com.BSMES.jd.main.dto.JmXj2TfDTO;
import com.BSMES.jd.main.dto.JmXj3TfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmXj2TfEntity;
import com.BSMES.jd.main.entity.JmXj3TfEntity;
import com.BSMES.jd.main.service.JmXj3TfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JmXj3TfServiceImpl extends BaseServiceImpl<JmXj3TfDao , JmXj3TfEntity , JmXj3TfDTO> implements JmXj3TfService {

    @Autowired
    JmXj3TfDao jmXj3TfDao;

    @Override
    public void beforeInsert(JmXj3TfDTO dto) {

    }

    @Override
    public void beforEedit(JmXj3TfDTO dto) {

    }

    @DS("master")
    @Override
    public CommonReturn getXj3Tf(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmXj3TfDTO> xj3Tfs = this.select(this.getQueryWrapper(dto));
        if(xj3Tfs.isEmpty()){
            result.setAll(20000,xj3Tfs,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,xj3Tfs,"查找成功");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn saveXj3Tf(JmXj3TfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 md_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid()) && dto.getCid()!=null ){
            QueryWrapper<JmXj3TfEntity> jmXj3TfQueryWrapper = new QueryWrapper<>();
            jmXj3TfQueryWrapper.eq("sid",dto.getSid());
            jmXj3TfQueryWrapper.eq("cid",dto.getCid());
            jmXj3TfQueryWrapper.eq("chk_no",dto.getChkNo());
            JmXj3TfDTO jmXj3TfDTO = this.selectOne(jmXj3TfQueryWrapper);
            //判断 usrcode 是否重复
            if (jmXj3TfDTO==null || jmXj3TfDTO.getSid()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"检验单号已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn saveXj3Tfs(List<JmXj3TfDTO> dtos) {
        CommonReturn result = new CommonReturn();
        try{
            jmXj3TfDao.saveJmXj3Tfs(dtos);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(10001, null, "操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn editXj3Tf(JmXj3TfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 dto 是否为空 判断 dto 的 md_no 是否有值
        if (dto!=null){

            //设置用户不能操作的属性
            try{
                this.edit(dto);
//                jmXj3TfDao.editJmXj3Tf(dto);
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
    public CommonReturn deleteXj3Tf(String sid) {
        CommonReturn result = new CommonReturn();
        try{
            jmXj3TfDao.deleteJmXj3Tf(sid);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(10001, null, "操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn delXj3Tf(List<String> sids, List<Integer> cids, List<String> chkNo) {
        CommonReturn result = new CommonReturn();
        //判断长度是否相等
        if (sids!=null && cids!=null && sids.size()!=0 && cids.size()!=0 && sids.size()==cids.size()){
            for (int i = 0 ; i < sids.size() ; i++){
                QueryWrapper<JmXj3TfEntity> jmXj3TfQueryWrapper = new QueryWrapper<>();
                jmXj3TfQueryWrapper.eq("sid",sids.get(i));
                jmXj3TfQueryWrapper.eq("cid",cids.get(i));
                jmXj3TfQueryWrapper.eq("chk_no",cids.get(i));
                try{
                    this.remove(jmXj3TfQueryWrapper);
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
    public CommonReturn getXj3TfPage(ResultType dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        IPage<JmXj3TfDTO> jmXj3TfDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        if (jmXj3TfDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmXj3TfDTOS,"查找成功");
        }
        return result;
    }

    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();

        if (dto.getAscOrder()!=null){
            queryWrapper.orderByAsc(MyUtils.humpToLine((String) dto.getAscOrder()));
        }
        if (dto.getDescOrder()!=null && dto.getAscOrder()==null){
            queryWrapper.orderByDesc(MyUtils.humpToLine((String) dto.getDescOrder()));
        }
        return queryWrapper;
    }
}
