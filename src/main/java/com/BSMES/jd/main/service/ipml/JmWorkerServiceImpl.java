package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmWorkerDao;
import com.BSMES.jd.main.dto.JmWorkerDTO;
import com.BSMES.jd.main.entity.JmWorkerEntity;
import com.BSMES.jd.main.service.JmWorkerService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JmWorkerServiceImpl extends BaseServiceImpl<JmWorkerDao , JmWorkerEntity , JmWorkerDTO> implements JmWorkerService {
    @Override
    public void beforeInsert(JmWorkerDTO dto) {

    }

    @Override
    public void beforEedit(JmWorkerDTO dto) {

    }

    @DS("master")
    @Override
    public CommonReturn getWorker(JmWorkerDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto,true);
        List<JmWorkerDTO> workers = this.select(data);
        if(workers.isEmpty()){
            result.setAll(20000,workers,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,workers,"查找成功");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn saveWorker(JmWorkerDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getWkNo())){
            QueryWrapper<JmWorkerEntity> workerQueryWrapper = new QueryWrapper<>();
            workerQueryWrapper.eq("wk_no",dto.getWkNo());
            JmWorkerDTO worker = this.selectOne(workerQueryWrapper);
            //判断 usrcode 是否重复
            if (worker==null || worker.getWkNo()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"人员号已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn editWorker(JmWorkerDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getWkNo())){
            //获取原先的人员属性值
            QueryWrapper<JmWorkerEntity> workerQueryWrapper = new QueryWrapper<>();
            workerQueryWrapper.eq("wk_no",dto.getWkNo());
            JmWorkerDTO worker = this.selectOne(workerQueryWrapper);
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

    @DS("master")
    @Override
    public CommonReturn delWorker(List<String> wkNos) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmWorkerEntity> workerQueryWrapper = new QueryWrapper<>();
        workerQueryWrapper.in("wk_no",wkNos);
        try{
            this.remove(workerQueryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn getWorkerPage(JmWorkerDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        IPage<JmWorkerDTO> jmWorkerDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmWorkerDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmWorkerDTOS,"查找成功");
        }
        return result;
    }
}
