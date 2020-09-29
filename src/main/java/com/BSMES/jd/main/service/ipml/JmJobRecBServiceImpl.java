package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmJobRecBDao;
import com.BSMES.jd.main.dto.JmJobDTO;
import com.BSMES.jd.main.dto.JmJobRecBDTO;
import com.BSMES.jd.main.dto.JmJobRecDTO;
import com.BSMES.jd.main.dto.JmMouldDTO;
import com.BSMES.jd.main.entity.JmJobRecBEntity;
import com.BSMES.jd.main.entity.JmJobRecEntity;
import com.BSMES.jd.main.entity.JmMouldEntity;
import com.BSMES.jd.main.service.JmJobRecBService;
import com.BSMES.jd.main.service.JmJobRecService;
import com.BSMES.jd.main.service.JmJobService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JmJobRecBServiceImpl extends BaseServiceImpl<JmJobRecBDao , JmJobRecBEntity , JmJobRecBDTO> implements JmJobRecBService {

    @Autowired
    JmJobRecService jmJobRecService;

    @Override
    public void beforeInsert(JmJobRecBDTO dto) {

    }

    @Override
    public void beforEedit(JmJobRecBDTO dto) {

    }

    @Override
    public CommonReturn getJobRecB(JmJobRecBDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto);
        List<JmJobRecBDTO> jobRecBs = this.select(data);
        if(jobRecBs.isEmpty()){
            result.setAll(20000,jobRecBs,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,jobRecBs,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveJobRecB(JmJobRecBDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 md_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getOpsid()) && dto.getCid()!=null ){
            QueryWrapper<JmJobRecBEntity> jobRecBQueryWrapper = new QueryWrapper<>();
            jobRecBQueryWrapper.eq("opsid",dto.getOpsid());
            jobRecBQueryWrapper.eq("cid",dto.getCid());
            JmJobRecBDTO jobRecB = this.selectOne(jobRecBQueryWrapper);
            //判断是否真的有opsid,存在
            QueryWrapper<JmJobRecEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("opsid",dto.getOpsid());
            JmJobRecDTO jmJobRecDTO = jmJobRecService.selectOne(queryWrapper);
            //判断 opsid 是否重复
            if ((jmJobRecDTO!=null && jmJobRecDTO.getOpsid()!=null) && (jobRecB==null || jobRecB.getOpsid()==null)){
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

    @Override
    public CommonReturn editJobRecB(JmJobRecBDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 md_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getOpsid()) && dto.getCid()!=null ){
            //获取原先的人员属性值
            QueryWrapper<JmJobRecBEntity> jobRecBQueryWrapper = new QueryWrapper<>();
            jobRecBQueryWrapper.eq("opsid",dto.getOpsid());
            jobRecBQueryWrapper.eq("cid",dto.getCid());
            JmJobRecBDTO jobRecB = this.selectOne(jobRecBQueryWrapper);
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
    public CommonReturn delJobRecB(List<String> opsids, List<Integer> cids) {
        CommonReturn result = new CommonReturn();
        //判断长度是否相等
        if (opsids!=null && cids!=null && opsids.size()!=0 && cids.size()!=0 && opsids.size()==cids.size()){
            for (int i = 0 ; i < opsids.size() ; i++){
                QueryWrapper<JmJobRecBEntity> jobRecBQueryWrapper = new QueryWrapper<>();
                jobRecBQueryWrapper.eq("opsid",opsids.get(i));
                jobRecBQueryWrapper.eq("cid",cids.get(i));
                try{
                    this.remove(jobRecBQueryWrapper);
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
    public CommonReturn getJobRecBPage(JmJobRecBDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        List<JmJobRecBDTO> jmJobRecBDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmJobRecBDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmJobRecBDTOS,"查找成功");
        }
        return result;
    }
}
