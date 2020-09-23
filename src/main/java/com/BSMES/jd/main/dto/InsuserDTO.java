package com.BSMES.jd.main.dto;


import com.BSMES.jd.common.dto.BaseDTO;
import com.BSMES.jd.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户映射表
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsuserDTO extends BaseDTO implements Serializable {

    private Integer cCorp;

    /**
     * 账户代码
     */
    private String usrcode;

    /**
     * 账户名称
     */
    private String usrname;

    private String usrnamexa;

    private String usrnamexb;

    /**
     * 简码
     */
    private String usrshort;

    /**
     * 密码
     */
    private String password;

    /**
     * 部门
     */
    private String orgcode;

    /**
     * 附加部门
     */
    private String orgcodeex;

    /**
     * 资料码
     */
    private String doccode;

    /**
     * 岗位
     */
    private String gwcode;

    /**
     * 性别编号
     */
    private String sexid;

    private String ident;

    private String address;

    private String codeno;

    /**
     * 属性(1:管理员;2:特权用户;3:公司用户;4:部门用户;5:普通用户;6:非操作员;9:做废)
     */
    private Integer usrattr;

    /**
     * 邮箱
     */
    private String semail;

    /**
     * 终止日期
     */
    private Date eymd;

    /**
     * 登录卡号
     */
    private String sicard;

    /**
     * 开始时间
     */
    private Integer ctfr;

    /**
     * 结束时间
     */
    private Integer ctto;

    private String skey;

    /**
     * 日期限制
     */
    private Integer dtlmt;

    private String tel;

    /**
     * 功能
     */
    private Integer bim;

    /**
     * 上级主管
     */
    private String upmang;

    /**
     * 自动执行
     */
    private String autocmd;

}
