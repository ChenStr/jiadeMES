package com.BSMES.jd.main.dto;

import com.BSMES.jd.common.dto.BaseDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典表
 */
@Data
public class JmBsDictionaryDTO extends BaseDTO {

    /**
     * 主键
     */
    public String id;

    /**
     * code
     */
    public String code;

    /**
     * 父子关联
     */
    public String pid;

    /**
     * 名称
     */
    public String name;

    /**
     * 状态
     */
    public Integer state;

    /**
     * 说明
     */
    public String rem;

    /**
     * 排序
     */
    public Integer sort;

    /**
     * 是否删除
     */
    public String isDel;

}
