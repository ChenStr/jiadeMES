package com.BSMES.jd.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 公共DTO内含基本所有DTO都需要的对象
 */
@Data
public class BaseDTO implements Serializable {
    /**
     * 反序列化
     */
//    private static final long serialVersionUID = 1L;

    /**
     * 第几页
     */
    public Integer page;

    /**
     * 每一页的数量
     */
    public Integer pageSize;

    /**
     * 排序字段
     */
    private Object ascOrder;

    /**
     * 排序字段
     */
    private Object descOrder;

    /**
     * 总共几页
     */
    private Integer total;
}
