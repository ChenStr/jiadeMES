package com.BSMES.jd.main.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 编码表映射
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("inssysvar")
public class InssysvarEntity {

    private Integer cCorp;

    /**
     * 变量名
     */
    private String sname;

    /**
     * 公式
     */
    private String sbds;

    /**
     * 参照值
     */
    private String sref;

    /**
     * 类型
     */
    private String chkflag;

    /**
     * 说明
     */
    private String remark;

    private String remarkxa;

    private String remarkxb;

    /**
     * 系统号
     */
    private Integer csysno;

    /**
     * 操作员
     */
    private String cuser;

    /**
     * 类别
     */
    private String stype;

    /**
     * 版本
     */
    private Integer cUlevel;

    private Integer ilevl;

    private String blast;

    private String bzLocked;

}
