package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmMtstdTfDao;
import com.BSMES.jd.main.dto.JmMtIdDTO;
import com.BSMES.jd.main.dto.JmMtstdMfDTO;
import com.BSMES.jd.main.dto.JmMtstdTf;
import com.BSMES.jd.main.dto.JmMtstdTfDTO;
import com.BSMES.jd.main.entity.JmMtIdEntity;
import com.BSMES.jd.main.entity.JmMtstdMfEntity;
import com.BSMES.jd.main.entity.JmMtstdTfEntity;
import com.BSMES.jd.main.service.JmMtIdService;
import com.BSMES.jd.main.service.JmMtstdMfService;
import com.BSMES.jd.main.service.JmMtstdTfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JmMtstdTfServiceImpl extends BaseServiceImpl<JmMtstdTfDao , JmMtstdTfEntity , JmMtstdTfDTO> implements JmMtstdTfService {

    @Autowired
    JmMtstdMfService jmMtstdMfService;

    @Autowired
    JmMtIdService jmMtIdService;

    @Autowired
    JmMtstdTfDao jmMtstdTfDao;

    @Override
    public void beforeInsert(JmMtstdTfDTO dto) {
        dto.setCreateDate(new Date());
    }

    @Override
    public void beforEedit(JmMtstdTfDTO dto) {

    }

    @DS("master")
    @Override
    public CommonReturn getMtstdTf(JmMtstdTfDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto,true);
        List<JmMtstdTfDTO> mtstdTf = this.select(data);
        if(mtstdTf.isEmpty()){
            result.setAll(20000,mtstdTf,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,mtstdTf,"查找成功");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn saveMtstdTf(JmMtstdTfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 md_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getMtstdNo()) && dto.getCid()!=null ){
            QueryWrapper<JmMtstdTfEntity> mtstdTfQueryWrapper = new QueryWrapper<>();
            mtstdTfQueryWrapper.eq("mtstd_no",dto.getMtstdNo()).and(mtstdTf->mtstdTf.eq("cid",dto.getCid()));
            JmMtstdTfDTO jmMtstdTf = this.selectOne(mtstdTfQueryWrapper);
            //判断 mtstd_no 是否存在
            QueryWrapper<JmMtstdMfEntity> jmMtstdMfEntityQueryWrapper = new QueryWrapper<>();
            jmMtstdMfEntityQueryWrapper.eq("mtstd_no",dto.getMtstdNo());
            JmMtstdMfDTO jmMtstdMfDTO = jmMtstdMfService.selectOne(jmMtstdMfEntityQueryWrapper);
            //判断检验标准是否存在
            QueryWrapper<JmMtIdEntity> jmMtIdEntityQueryWrapper = new QueryWrapper<>();
            jmMtIdEntityQueryWrapper.eq("mt_id",dto.getMtId());
            JmMtIdDTO JmMtIdDTO = jmMtIdService.selectOne(jmMtIdEntityQueryWrapper);
            Boolean flag = jmMtstdTf==null || jmMtstdTf.getMtstdNo()==null;
            //判断 mtstd_no 是否重复
            if ((JmMtIdDTO!=null && JmMtIdDTO.getMtId()!=null) && (jmMtstdMfDTO!=null && jmMtstdMfDTO.getMtstdNo()!=null) &&(jmMtstdTf==null || jmMtstdTf.getMtstdNo()==null)){
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

    @DS("master")
    @Override
    public CommonReturn saveMtstdTfs(List<JmMtstdTf> dtos) {
        CommonReturn result = new CommonReturn();
        try{
            jmMtstdTfDao.SaveJmMtstdTfs(dtos);
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return null;
    }

    @DS("master")
    @Override
    public CommonReturn editMtstdTf(JmMtstdTfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 md_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getMtstdNo()) && dto.getCid()!=null ){
            //获取原先的人员属性值
            QueryWrapper<JmMtstdTfEntity> mtstdTfQueryWrapper = new QueryWrapper<>();
            mtstdTfQueryWrapper.eq("mtstd_no",dto.getMtstdNo());
            mtstdTfQueryWrapper.eq("cid",dto.getCid());
            JmMtstdTfDTO jmMtstdTf = this.selectOne(mtstdTfQueryWrapper);
            //设置用户不能操作的属性
            try{
//                this.edit(dto);
                jmMtstdTfDao.UpdateJmMtstdTf(dto);
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

    @DS("master")
    @Override
    public CommonReturn delMtstdTf(List<String> mtstdNos, List<Integer> cids) {
        CommonReturn result = new CommonReturn();
        //判断长度是否相等
        if (mtstdNos!=null && cids!=null && mtstdNos.size()!=0 && cids.size()!=0 && mtstdNos.size()==cids.size()){
            for (int i = 0 ; i < mtstdNos.size() ; i++){
                QueryWrapper<JmMtstdTfEntity> mtstdTfQueryWrapper = new QueryWrapper<>();
                mtstdTfQueryWrapper.eq("mtstd_no",mtstdNos.get(i));
                mtstdTfQueryWrapper.eq("cid",cids.get(i));
                try{
                    this.remove(mtstdTfQueryWrapper);
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
    public CommonReturn getMtstdTfPage(JmMtstdTfDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        IPage<JmMtstdTfDTO> jmMtstdTfDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmMtstdTfDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmMtstdTfDTOS,"查找成功");
        }
        return result;
    }
}
