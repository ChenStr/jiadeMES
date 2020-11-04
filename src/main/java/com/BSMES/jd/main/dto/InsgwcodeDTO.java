package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 岗位定义
 */
@Data
public class InsgwcodeDTO extends BaseDTO {

    /**
     * 集团
     */
    public Integer cCorp;

    /**
     * 编码
     */
    @TableId
    public String gwcode;

    /**
     * 名称
     */
    public String gwname;

    /**
     * 说明
     */
    public String smemo;

}
