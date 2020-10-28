package com.BSMES.jd.main.controller;


import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.main.dto.JmJobRecBDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.service.JmJobRecBService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/jobrecb")
@RestController
public class JmJobRecBController {

    @Autowired
    JmJobRecBService jmJobRecBService;

    @GetMapping()
    public CommonReturn getJobRecB(ResultType dto, Boolean isPage){
        CommonReturn result = new CommonReturn();
        if (isPage==null || isPage==false){
            result = jmJobRecBService.getJobRecB(dto);
        }else{
            result = jmJobRecBService.getJobRecBPage(dto);
        }
        return result;
    }

    @PostMapping()
    public CommonReturn saveJobRecB(@RequestBody JmJobRecBDTO dto){
        CommonReturn result = new CommonReturn();
        result = jmJobRecBService.saveJobRecB(dto);
        return result;
    }

    @PutMapping()
    public CommonReturn editJobRecB(@RequestBody List<JmJobRecBDTO> dtos){
        CommonReturn result = new CommonReturn();
//        result = jmJobRecBService.editJobRecB(dto);
        result = jmJobRecBService.editJobRecBs(dtos);
        return result;
    }

    @DeleteMapping()
    public CommonReturn delJobRecB( String[] opsids , Integer[] cids ){
        CommonReturn result = new CommonReturn();
        List<String> opsids1 = java.util.Arrays.asList(opsids);
        List<Integer> cids1 = java.util.Arrays.asList(cids);
        result = jmJobRecBService.delJobRecB(opsids1,cids1);
        return result;
    }

    /**
     * 车间生产日报表
     * @param dto
     * @return
     */
    @GetMapping("/dayreport")
    public CommonReturn getDayReport(ResultType dto){
        CommonReturn result = new CommonReturn();
        result = jmJobRecBService.getJobRecReportPage(dto);
        return result;
    }

    /**
     * 车间生产日报表 Excel 导出
     * @param dto
     * @return
     */
    @GetMapping("/sorgdayexcel")
    public CommonReturn getSorgDayExcel(ResultType dto){
        CommonReturn result = new CommonReturn();
        result = jmJobRecBService.getSorgDayReportExcel(dto);
        return result;
    }

    /**
     * 人员生产月报表
     * @param dto
     * @return
     */
    @GetMapping("/monreport")
    public CommonReturn getJobRecMonReport(ResultType dto){
        CommonReturn result = new CommonReturn();
        result = jmJobRecBService.getJobRecMonReport(dto);
        return result;
    }

    /**
     * 人员生产月报表 Excel 导出
     * @param dto
     * @return
     */
    @GetMapping("/usermonexcel")
    public CommonReturn getUserMonExcel(ResultType dto){
        CommonReturn result = new CommonReturn();
        result = jmJobRecBService.getUserMonReportExcel(dto);
        return result;
    }

    /**
     * 设备生产月报表
     * @param dto
     * @return
     */
    @GetMapping("/mondevreport")
    public CommonReturn getJobRecRsNoMonReport(ResultType dto){
        CommonReturn result = new CommonReturn();
        result = jmJobRecBService.getJobRecRsNoMonReport(dto);
        return result;
    }

}
