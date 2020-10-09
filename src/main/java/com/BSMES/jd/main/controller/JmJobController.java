package com.BSMES.jd.main.controller;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmDevDTO;
import com.BSMES.jd.main.dto.JmJobDTO;
import com.BSMES.jd.main.dto.JobJoin;
import com.BSMES.jd.main.dto.JobSave;
import com.BSMES.jd.main.service.JmJobService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
public class JmJobController {

    @Autowired
    JmJobService jmJobService;

    @GetMapping("/plan")
    public CommonReturn getJmJoblook(JobJoin dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmJobService.joinFindJobs(dto);
        }else{
            result = jmJobService.getJobJoinPage(dto);
        }
        return result;
    }

    @GetMapping("")
    public CommonReturn getJmJob(JmJobDTO dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmJobService.getJob(dto);
        }else{
            QueryWrapper queryWrapper = new QueryWrapper();
            result = jmJobService.getJobPage(dto,queryWrapper);
        }
        return result;
    }


    @PostMapping()
    public CommonReturn saveJmJobs(@RequestBody JobSave jobSave){
        CommonReturn result = new CommonReturn();
//        result = jmJobService.saveJob(dto);
        result = jmJobService.saveJobs(jobSave);
        return result;
    }

    @PutMapping()
    @Transactional
    public CommonReturn editJmJob(@RequestBody List<JmJobDTO> dtos){
        CommonReturn result = new CommonReturn();
        if (dtos!=null && dtos.size()>0){
            for (JmJobDTO dto : dtos){
                try{
                    result = jmJobService.editJob(dto);
                }catch (Exception e){
                    result.setAll(10001,null,"编辑失败");
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    @DeleteMapping()
    public CommonReturn delJmJob(String[] sids, Integer[] cids){
        CommonReturn result = new CommonReturn();
        List<String> sids2 = java.util.Arrays.asList(sids);
        List<Integer> cids2 = java.util.Arrays.asList(cids);
        result = jmJobService.delJob(sids2,cids2);
        return result;
    }

}
