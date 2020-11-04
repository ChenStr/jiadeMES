package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * 仓库储位
 */
@Data
public class JmWhLocDTO extends BaseDTO {

    /**
     * 储位代号
     */
    public String whLoc;

    /**
     * 名称
     */
    public String name;

    /**
     * 仓库
     */
    public String wh;

    /**
     * 备注
     */
    public String rem;

    /**
     * 制单人
     */
    public String smake;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date hpdate;

    /**
     * 排序
     */
    public Integer sort;

}
