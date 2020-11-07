package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 菜单定义
 */
@Data
public class InsmenuDTO extends BaseDTO {

    /**
     * 集团
     */
    public Integer cCorp;

    /**
     * 编号
     */
    public String menuid;

    /**
     * 名称
     */
    public String menuname;

    /**
     * 属性
     */
    public Integer menuattr;

    /**
     * 终端类别
     */
    public Integer mbtype;

    /**
     * 版本
     */
    public Integer cUlevel;

}
