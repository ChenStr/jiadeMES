package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmJobDao;
import com.BSMES.jd.main.dto.JmJobDTO;
import com.BSMES.jd.main.dto.JmMoMfDTO;
import com.BSMES.jd.main.entity.JmJobEntity;
import com.BSMES.jd.main.entity.JmMoMfEntity;
import com.BSMES.jd.main.service.JmJobService;
import com.BSMES.jd.main.service.JmMoMfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JmJobServiceImpl extends BaseServiceImpl<JmJobDao , JmJobEntity , JmJobDTO> implements JmJobService {

    @Autowired
    JmMoMfService jmMoMfService;

    @Override
    public void beforeInsert(JmJobDTO dto) {

    }

    @Override
    public void beforEedit(JmJobDTO dto) {

    }

    @Override
    public CommonReturn getJob(JmJobDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto);
        List<JmJobDTO> jobs = this.select(data);
        if(jobs.isEmpty()){
            result.setAll(20000,jobs,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,jobs,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveJob(JmJobDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid()) && dto.getCid()!=null ){
            QueryWrapper<JmJobEntity> jobQueryWrapper = new QueryWrapper<>();
            //判断制令单号是否存在 , 不存在不能添加
            QueryWrapper<JmMoMfEntity> moMfQueryWrapper = new QueryWrapper<>();
            JmMoMfDTO jmMoMf = jmMoMfService.selectOne(moMfQueryWrapper.eq("sid",dto.getSid()));
            if (jmMoMf==null || jmMoMf.getSid() == null){
                result.setAll(10001,null,"制令单号不存在不能新增，不能新增!");
            }else{
                jobQueryWrapper.eq("sid",dto.getSid());
                jobQueryWrapper.eq("cid",dto.getCid());
                JmJobDTO job = this.selectOne(jobQueryWrapper);
                //判断 usrcode 是否重复
                if (job==null || job.getSid()==null){
                    this.insert(dto);
                    result.setAll(20000,null,"操作成功");
                }else{
                    result.setAll(10001,null,"生产计划单已经存在，不能新增!");
                }
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn editJob(JmJobDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid()) && dto.getCid()!=null ){
            //获取原先的人员属性值
            QueryWrapper<JmJobEntity> jobQueryWrapper = new QueryWrapper<>();
            jobQueryWrapper.eq("sid",dto.getSid());
            jobQueryWrapper.eq("cid",dto.getCid());
            JmJobDTO job = this.selectOne(jobQueryWrapper);
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
    public CommonReturn delJob(List<String> sids, List<Integer> cids) {
        CommonReturn result = new CommonReturn();
        //判断长度是否相等
        if (sids!=null && cids!=null && sids.size()!=0 && cids.size()!=0 && sids.size()==cids.size()){
//        jobQueryWrapper.in("sid",sids);
//        jobQueryWrapper.and(wrapper -> wrapper.in("cid", cids));
            for (int i = 0 ; i < sids.size() ; i++){
                QueryWrapper<JmJobEntity> jobQueryWrapper = new QueryWrapper<>();
                jobQueryWrapper.eq("sid",sids.get(i));
                jobQueryWrapper.eq("cid",cids.get(i));
                try{
                    this.remove(jobQueryWrapper);
                    result.setAll(20000,null,"操作成功");
                }catch (Exception e) {
                    result.setAll(20000, null, "操作失败");
                    return result;
                }
            }

        }else{
            result.setAll(10001,null,"操作失败");
        }

        return result;
    }

    @Override
    public CommonReturn getJobPage(JmJobDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        List<JmJobDTO> jmJobDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmJobDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmJobDTOS,"查找成功");
        }
        return result;
    }
}
