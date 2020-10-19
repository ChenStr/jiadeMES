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
public class InsuserDTO extends BaseDTO implements Serializable {

    public Integer cCorp;

    /**
     * 账户代码
     */
    public String usrcode;

    /**
     * 账户名称
     */
    public String usrname;

    public String usrnamexa;

    public String usrnamexb;

    /**
     * 简码
     */
    public String usrshort;

    /**
     * 密码
     */
    public String password;

    /**
    /**
     * 部门
     */
    public String orgcode;

    /**
     * 附加部门
     */
    public String orgcodeex;

    /**
     * 资料码
     */
    public String doccode;

    /**
     * 岗位
     */
    public String gwcode;

    /**
     * 性别编号
     */
    public String sexid;

    public String ident;

    public String address;

    public String codeno;

    /**
     * 属性(1:管理员;2:特权用户;3:公司用户;4:部门用户;5:普通用户;6:非操作员;9:做废)
     */
    public Integer usrattr;

    /**
     * 邮箱
     */
    public String semail;

    /**
     * 终止日期
     */
    public Date eymd;

    /**
     * 登录卡号
     */
    public String sicard;

    /**
     * 开始时间
     */
    public Integer ctfr;

    /**
     * 结束时间
     */
    public Integer ctto;

    public String skey;

    /**
     * 日期限制
     */
    public Integer dtlmt;

    public String tel;

    /**
     * 功能
     */
    public Integer bim;

    /**
     * 上级主管
     */
    public String upmang;

    /**
     * 自动执行
     */
    public String autocmd;

    /**
     * BS端密码
     */
    public String pswd;

    /**
     * 备注
     */
    public String rem;

    /**
     * 是否超管
     */
    public String isSupper;

    /**
     * 状态
     */
    public String state;

}
