package com.BSMES.jd.main.dto;

import lombok.Data;

/**
 * 岗位菜单中间表
 */
@Data
public class InslimitDTO {

    /**
     * 集团
     */
    public Integer cCorp;

    /**
     * 岗位人员码
     */
    public String gwuser;

    /**
     * 菜单号
     */
    public String menuid;

    /**
     * 岗位
     */
    public String cgw;

}
