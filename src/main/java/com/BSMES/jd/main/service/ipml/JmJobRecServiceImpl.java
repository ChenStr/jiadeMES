package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmJobRecDao;
import com.BSMES.jd.main.dto.JmJobDTO;
import com.BSMES.jd.main.dto.JmJobRecBDTO;
import com.BSMES.jd.main.dto.JmJobRecDTO;
import com.BSMES.jd.main.entity.JmJobEntity;
import com.BSMES.jd.main.entity.JmJobRecBEntity;
import com.BSMES.jd.main.entity.JmJobRecEntity;
import com.BSMES.jd.main.service.InssysvarService;
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
public class JmJobRecServiceImpl extends BaseServiceImpl<JmJobRecDao , JmJobRecEntity , JmJobRecDTO> implements JmJobRecService {


    @Autowired
    JmJobService jmJobService;

    @Autowired
    JmJobRecBService jmJobRecBService;

    @Autowired
    InssysvarService inssysvarService;

    @Override
    public void beforeInsert(JmJobRecDTO dto) {

    }

    @Override
    public void beforEedit(JmJobRecDTO dto) {

    }

    @Override
    public CommonReturn getJobRec(JmJobRecDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto);
        List<JmJobRecDTO> jobRec = this.select(data);
        if(jobRec.isEmpty()){
            result.setAll(20000,jobRec,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,jobRec,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveJobRec(JmJobRecDTO dto) {
        CommonReturn result = new CommonReturn();
        dto.setOpsid(this.getKey("JmJobRec","opsid",inssysvarService,dto));
        //判断dto是否为空 判断dto的 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getOpsid())){
            QueryWrapper<JmJobRecEntity> jobRecQueryWrapper = new QueryWrapper<>();
            jobRecQueryWrapper.eq("opsid",dto.getOpsid());
            JmJobRecDTO jobRec = this.selectOne(jobRecQueryWrapper);
            //判断计划单号是否存在
            QueryWrapper<JmJobEntity> jmJobQueryWrapper = new QueryWrapper<>();
            jmJobQueryWrapper.eq("jb_no",dto.getJbNo());
            JmJobDTO jmJobDTO = jmJobService.selectOne(jmJobQueryWrapper);
            //判断 opsid 是否重复
//            Boolean flag1 = (jmJobDTO!=null && jmJobDTO.getJbNo()!=null);
//            Boolean flag2 = (jobRec==null || jobRec.getOpsid()==null);
            if ( (jmJobDTO!=null && jmJobDTO.getJbNo()!=null) &&(jobRec==null || jobRec.getOpsid()==null)){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"随工单号已经存在或计划单号不存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn editJobRec(JmJobRecDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getOpsid())){
            //获取原先的用户属性值
            QueryWrapper<JmJobRecEntity> jmJobRecQueryWrapper = new QueryWrapper<>();
            jmJobRecQueryWrapper.eq("opsid",dto.getOpsid());
            JmJobRecDTO var = this.selectOne(jmJobRecQueryWrapper);
            //设置用户不能操作的属性
            dto.setJbNo(var.getJbNo());
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
    public CommonReturn delJobRec(List<String> opsids) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmJobRecEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("opsid",opsids);
        //查看本张随工单下是否有明细表
        QueryWrapper<JmJobRecBEntity> jmJobRecBEntityQueryWrapper = new QueryWrapper<>();
        jmJobRecBEntityQueryWrapper.in("opsid",opsids);
        List<JmJobRecBDTO> jmJobRecBDTOS = jmJobRecBService.select(jmJobRecBEntityQueryWrapper);
        if (jmJobRecBDTOS==null || jmJobRecBDTOS.size()==0){
            try{
                this.remove(queryWrapper);
                result.setAll(20000,null,"操作成功");
            }catch (Exception e) {
                result.setAll(10001, null, "操作失败");
            }
        }else{
            result.setAll(10001, null, "本随工单下已经有明细了不能删除");
        }

        return result;
    }

    @Override
    public CommonReturn getJobRecPage(JmJobRecDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        List<JmJobRecDTO> jmJobRecDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmJobRecDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmJobRecDTOS,"查找成功");
        }
        return result;
    }
}
