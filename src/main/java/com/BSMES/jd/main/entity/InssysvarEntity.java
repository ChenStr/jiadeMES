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

    public Integer cCorp;

    /**
     * 变量名
     */
    public String sname;

    /**
     * 公式
     */
    public String sbds;

    /**
     * 参照值
     */
    public String sref;

    /**
     * 类型
     */
    public String chkflag;

    /**
     * 说明
     */
    public String remark;

    public String remarkxa;

    public String remarkxb;

    /**
     * 系统号
     */
    public Integer csysno;

    /**
     * 操作员
     */
    public String cuser;

    /**
     * 类别
     */
    public String stype;

    /**
     * 版本
     */
    public Integer cUlevel;

    public Integer ilevl;

    public String blast;

    public String bzLocked;

}
