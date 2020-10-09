package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class JmXjMf2 extends BaseDTO implements Serializable {

    JmXjMfDTO jmXjMfDTO;

    //部门
    InsorgDTO insorgDTO;

    //产品
    JmPrdtDTO jmPrdtDTO;

    //模具
    JmMouldDTO jmMouldDTO;

    //设备
    JmDevDTO jmDevDTO;


    List<JmXjMf> jmXjMfs;

}
