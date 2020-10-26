package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmDevMtidDao;
import com.BSMES.jd.main.dto.JmDevDTO;
import com.BSMES.jd.main.dto.JmDevMtidDTO;
import com.BSMES.jd.main.entity.JmDevMtidEntity;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.main.service.JmDevMtidService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JmDevMtidServiceImpl extends BaseServiceImpl<JmDevMtidDao, JmDevMtidEntity , JmDevMtidDTO> implements JmDevMtidService {


    @Autowired
    JmDevMtidDao jmDevMtidDao;

    @Autowired
    InssysvarService inssysvarService;

    @Override
    public void beforeInsert(JmDevMtidDTO dto) {

    }

    @Override
    public void beforEedit(JmDevMtidDTO dto) {

    }

    @Override
    public CommonReturn getDevMtid(JmDevMtidDTO dto) {
        CommonReturn result = new CommonReturn();
        List<JmDevMtidDTO> moulds = this.select(getQueryWrapper(dto));
        if(moulds.isEmpty()){
            result.setAll(20000,moulds,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,moulds,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveDevMtid(JmDevMtidDTO dto) {
        CommonReturn result = new CommonReturn();
        if (dto.getMtId()==null){
            dto.setMtId(getKey("Mt","mt_id",inssysvarService,dto));
        }
        //判断dto是否为空 判断dto的 md_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getDevid()) && dto.getMtId()!=null){
            QueryWrapper<JmDevMtidEntity> jmDevMtidEntityQueryWrapper = new QueryWrapper<>();
            jmDevMtidEntityQueryWrapper.eq("devid",dto.getDevid());
            jmDevMtidEntityQueryWrapper.eq("mt_id",dto.getMtId());
            JmDevMtidDTO JmMould = this.selectOne(jmDevMtidEntityQueryWrapper);
            //判断 usrcode 是否重复
            if (JmMould==null || JmMould.getDevid()==null){
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

    @Override
    public CommonReturn editDevMtid(JmDevMtidDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 md_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getDevid()) && dto.getMtId()!=null ){
            //获取原先的人员属性值
            QueryWrapper<JmDevMtidEntity> jmDevMtidEntityQueryWrapper = new QueryWrapper<>();
            jmDevMtidEntityQueryWrapper.eq("devid",dto.getDevid());
            jmDevMtidEntityQueryWrapper.eq("mt_id",dto.getMtId());
            JmDevMtidDTO JmMould = this.selectOne(jmDevMtidEntityQueryWrapper);
            //设置用户不能操作的属性
            try{
//                this.edit(dto);
                jmDevMtidDao.edit(dto);
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
    public CommonReturn delDevMtid(List<String> devids, List<String> mtIds) {
        CommonReturn result = new CommonReturn();
        //判断长度是否相等
        if (devids!=null && mtIds!=null && devids.size()!=0 && mtIds.size()!=0 && devids.size()==mtIds.size()){
            for (int i = 0 ; i < devids.size() ; i++){
                QueryWrapper<JmDevMtidEntity> jmDevMtidEntityQueryWrapper = new QueryWrapper<>();
                jmDevMtidEntityQueryWrapper.eq("devid",devids.get(i));
                jmDevMtidEntityQueryWrapper.eq("mt_id",mtIds.get(i));
                try{
                    this.remove(jmDevMtidEntityQueryWrapper);
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

    @Override
    public CommonReturn getDevMtidPage(JmDevMtidDTO dto) {
        CommonReturn result = new CommonReturn();

        if (dto.getPage()==null){
            dto.setPage(1);
        }
        if (dto.getPageSize()==null){
            dto.setPageSize(10);
        }
        try{
            PageHelper.startPage(dto.getPage(), dto.getPageSize());
            List<JmDevMtidDTO> dev = (List<JmDevMtidDTO>) this.getDevMtid(dto).getData();
            PageInfo pageInfo = new PageInfo<JmDevMtidDTO>(dev);
            pageInfo.setTotal(((List<JmDevMtidDTO>) this.getDevMtid(dto).getData()).size());
            result.setAll(20000,pageInfo,"操作成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setAll(40000,null,"操作失败");
        }

        return result;
    }

    /**
     * 填写筛选数据
     * @param dto
     * @return
     */
    private QueryWrapper getQueryWrapper(JmDevMtidDTO dto){
        QueryWrapper queryWrapper = new QueryWrapper();

        if (MyUtils.StringIsNull(dto.getDevNo())){
            queryWrapper.eq("dev_no",dto.getDevNo());
        }
        if (MyUtils.StringIsNull(dto.getDevid())){
            queryWrapper.eq("devid",dto.getDevid());
        }
        if (MyUtils.StringIsNull(dto.getMtId())){
            queryWrapper.eq("mt_id",dto.getMtId());
        }
        if (MyUtils.StringIsNull(dto.getMtName())){
            queryWrapper.eq("mt_name",dto.getMtName());
        }
        if (MyUtils.StringIsNull(dto.getMtRem())){
            queryWrapper.eq("mt_rem",dto.getMtRem());
        }

        return queryWrapper;
    }
}
