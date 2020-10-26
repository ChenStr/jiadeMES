package com.BSMES.jd.main.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("jm_bs_dictionary")
public class JmBsDictionaryEntity {

    /**
     * 编码
     */
    @TableId
    public String code;

    /**
     * 分类代号
     */
    public String typeId;

    /**
     * 分类名称
     */
    public String typeName;

    /**
     * 项次
     */
    public String cid;

    /**
     * 名称
     */
    public String codeName;

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

}
