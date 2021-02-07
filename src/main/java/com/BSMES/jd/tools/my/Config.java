package com.BSMES.jd.tools.my;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 配置地址类
 */
@Component
@Data
public class Config {
    @Value("${excel.address}")
    private String address;


}
