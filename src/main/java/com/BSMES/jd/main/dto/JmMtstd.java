package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

@Data
public class JmMtstd extends BaseDTO {

    //设备信息
    JmDevDTO jmDevDTO;

    //点检主表
    JmMtstdMfDTO jmMtstdMfDTO;

//    //点检子表
//    List<JmMtstdTfDTO> jmMtstdTfDTOs;

    //点检子表
    List<JmMtstdTf> jmMtstdTfs;
}
