package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.InsuserDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.InsuserEntity;
import com.BSMES.jd.main.entity.JmDevEntity;
import com.BSMES.jd.main.entity.JmDevSalEntity;
import com.BSMES.jd.main.entity.JmWorkerEntity;
import com.BSMES.jd.main.service.InsuserService;
import com.BSMES.jd.main.service.JmDevSalService;
import com.BSMES.jd.main.service.JmDevService;
import com.BSMES.jd.main.service.JmWorkerService;
import com.BSMES.jd.tools.my.MyUtils;
import com.BSMES.jd.tools.password.PasswordUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class InsuserServiceImpl extends BaseServiceImpl<InsuserDao , InsuserEntity , InsuserDTO> implements InsuserService {

    @Autowired
    JmWorkerService jmWorkerService;

    @Autowired
    JmDevSalService jmDevSalService;

    @Autowired
    JmDevService jmDevService;

    @Override
    public void beforeInsert(InsuserDTO dto) {
        //首先先将cCorp默认值加上
        dto.setCCorp(0);
    }

    @Override
    public void beforEedit(InsuserDTO dto) {

    }

    @Override
    public CommonReturn getUser(InsuserDTO dto) {
        CommonReturn result = new CommonReturn();
        List<UserPlus> userAndWorkers = new ArrayList<>();
        List<InsuserDTO> users = this.select(this.getQueryWrapper(dto));
        if(users.isEmpty()){
            result.setAll(20000,users,"没有查找结果，建议仔细核对查找条件");
        }else{
            //将使用人信息也带出来
            for (InsuserDTO user : users){
                UserPlus userAndWorker = new UserPlus();
                userAndWorker.setInsuserDTO(user);
                //去人员表将用户的人员也找出来
                QueryWrapper<JmWorkerEntity> jmWorkerEntityQueryWrapper = new QueryWrapper<>();
                jmWorkerEntityQueryWrapper.eq("wk_no",user.getUsrcode());
                JmWorkerDTO jmWorkerDTO = jmWorkerService.selectOne(jmWorkerEntityQueryWrapper);
                if (jmWorkerDTO!=null && jmWorkerDTO.getWkNo()!=null){
                    userAndWorker.setJmWorkerDTO(jmWorkerDTO);
                }
                userAndWorkers.add(userAndWorker);
            }
            result.setAll(20000,userAndWorkers,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn getUserPlus(InsuserDTO dto) {
        CommonReturn result = new CommonReturn();
        List<UserPlus> userPluses = new ArrayList<>();
        List<InsuserDTO> users = this.select(this.getQueryWrapper(dto));
        if(users.isEmpty()){
            for (InsuserDTO user : users){

                UserPlus userPlus = new UserPlus();

                //查询员工信息
                QueryWrapper<JmWorkerEntity> jmWorkerEntityQueryWrapper = new QueryWrapper<>();
                jmWorkerEntityQueryWrapper.eq("worker",user.getUsrcode());
                JmWorkerDTO jmWorkerDTO = jmWorkerService.selectOne(jmWorkerEntityQueryWrapper);

                userPlus.setInsuserDTO(user);
                userPlus.setJmWorkerDTO(jmWorkerDTO);
                userPluses.add(userPlus);

            }
            result.setAll(20000,users,"没有查找结果，建议仔细核对查找条件");
        }else{

            result.setAll(20000,users,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn login(InsuserDTO dto) {
        CommonReturn result = new CommonReturn();
        UserPlus userPlus = new UserPlus();
        //首先判断是否是工位机登录还是PC端登录
        if(dto!=null && dto.getSicard()!=null){
            //通过卡号将人员与账号信息查出来
            QueryWrapper<InsuserEntity> insuserEntityQueryWrapper = new QueryWrapper<>();
            insuserEntityQueryWrapper.eq("sicard",dto.getSicard());
            InsuserDTO insuserDTO = this.selectOne(insuserEntityQueryWrapper);

            //判断是否有查询到数据
            if (insuserDTO!=null && insuserDTO.getUsrcode()!=null){
                //去人员表将用户的人员也找出来
                QueryWrapper<JmWorkerEntity> jmWorkerEntityQueryWrapper = new QueryWrapper<>();
                jmWorkerEntityQueryWrapper.eq("wk_no",insuserDTO.getUsrcode());
                JmWorkerDTO jmWorkerDTO = jmWorkerService.selectOne(jmWorkerEntityQueryWrapper);
                //去人员设备管理表中查出人员对应的设备
                QueryWrapper<JmDevSalEntity> jmDevSalEntityQueryWrapper = new QueryWrapper<>();
                jmDevSalEntityQueryWrapper.eq("sal_no",insuserDTO.getUsrcode());
                List<JmDevSalDTO> jmDevSalDTOS = jmDevSalService.select(jmDevSalEntityQueryWrapper);
                userPlus.setInsuserDTO(insuserDTO);
//                if (jmWorkerDTO!=null && jmWorkerDTO.getWkNo()!=null){
                    userPlus.setJmWorkerDTO(jmWorkerDTO);
//                }
                //检验员需要返回
                if ("03".equals(insuserDTO.getGwcode()) || "04".equals(insuserDTO.getGwcode())){
                    QueryWrapper<JmDevEntity> jmDevEntityQueryWrapper = new QueryWrapper<>();
                    jmDevEntityQueryWrapper.eq("sorg",insuserDTO.getOrgcode());
                    List<JmDevDTO> jmDevDTOS = jmDevService.select(jmDevEntityQueryWrapper);
                    if (jmDevDTOS!=null && jmDevDTOS.size()>0){
                        userPlus.setJmDevDTOS(jmDevDTOS);
                    }
                }else{
                    //查询中间表
                    List<String> devNos = new ArrayList<>();
                    jmDevSalDTOS.stream().forEach(T->devNos.add(T.getDevNo()));
                    if (devNos!=null && devNos.size()>0){
                        QueryWrapper<JmDevEntity> jmDevEntityQueryWrapper = new QueryWrapper<>();
                        jmDevEntityQueryWrapper.in("dev_no",devNos);
                        List<JmDevDTO> jmDevDTOS = jmDevService.select(jmDevEntityQueryWrapper);
                        userPlus.setJmDevDTOS(jmDevDTOS);
                    }
                }
            }else{
                result.setAll(40000,null,"卡号不存在");
                return result;
            }


            result.setAll(20000,userPlus,"登录成功");
        }else if (dto.getUsrcode()!=null && dto.getPswd()!=null){
            //根据这个用户名查询用户
            QueryWrapper<InsuserEntity> insuserEntityQueryWrapper = new QueryWrapper<>();
            insuserEntityQueryWrapper.eq("usrcode",dto.getUsrcode());
            InsuserDTO insuserDTO = this.selectOne(insuserEntityQueryWrapper);
            //判断用户的账户是否存在，密码是否正确
            if (insuserDTO!=null && insuserDTO.getPswd()!=null && PasswordUtils.matches(dto.getPswd(),insuserDTO.getPswd())){
                userPlus.setInsuserDTO(insuserDTO);
                //去人员表将用户的人员也找出来
                QueryWrapper<JmWorkerEntity> jmWorkerEntityQueryWrapper = new QueryWrapper<>();
                jmWorkerEntityQueryWrapper.eq("wk_no",dto.getUsrcode());
                JmWorkerDTO jmWorkerDTO = jmWorkerService.selectOne(jmWorkerEntityQueryWrapper);
//                if (jmWorkerDTO!=null && jmWorkerDTO.getWkNo()!=null){
                    userPlus.setJmWorkerDTO(jmWorkerDTO);
//                }

                //去人员设备管理表中查出人员对应的设备
                QueryWrapper<JmDevSalEntity> jmDevSalEntityQueryWrapper = new QueryWrapper<>();
                jmDevSalEntityQueryWrapper.eq("sal_no",insuserDTO.getUsrcode());
                List<JmDevSalDTO> jmDevSalDTOS = jmDevSalService.select(jmDevSalEntityQueryWrapper);

                List<String> devNos = new ArrayList<>();
                jmDevSalDTOS.stream().forEach(T->devNos.add(T.getDevNo()));
                if (devNos!=null && devNos.size()>0){
                    QueryWrapper<JmDevEntity> jmDevEntityQueryWrapper = new QueryWrapper<>();
                    jmDevEntityQueryWrapper.in("dev_no",devNos);
                    List<JmDevDTO> jmDevDTOS = jmDevService.select(jmDevEntityQueryWrapper);
                    userPlus.setJmDevDTOS(jmDevDTOS);
                }


                result.setAll(20000,userPlus,"登录成功");
            }else{
                result.setAll(10001,null,"账号不存在或密码不正确");
            }
        }else{
            result.setAll(40000,null,"登录失败");
        }
        return result;
    }

    @Override
    public CommonReturn saveUser(InsuserDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的usrcode是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getUsrcode())){
            QueryWrapper<InsuserEntity> insuserQueryWrapper = new QueryWrapper<>();
            insuserQueryWrapper.eq("usrcode",dto.getUsrcode());
            InsuserDTO user = this.selectOne(insuserQueryWrapper);
            //判断 usrcode 是否重复
            if (user==null || user.getUsrcode()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"用户已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn editUser(InsuserDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getUsrcode())){
            //获取原先的用户属性值
            QueryWrapper<InsuserEntity> insuserqueryWrapper = new QueryWrapper<>();
            insuserqueryWrapper.eq("usrcode",dto.getUsrcode());
            InsuserDTO user = this.selectOne(insuserqueryWrapper);
            //设置用户不能操作的属性
            dto.setCCorp(user.getCCorp());
            try{
                this.edit(dto);
                result.setAll(20000,null,"操作成功");
            }catch (Exception e){
                result.setAll(10001,null,"操作失败");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn delUser(List<String> usrcodes) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<InsuserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("usrcode",usrcodes);
        try{
            this.remove(queryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getUserPage(InsuserDTO dto) {
        CommonReturn result = new CommonReturn();
        List<UserPlus> userAndWorkers = new ArrayList<>();
        QueryWrapper queryWrapper = this.getQueryWrapper(dto);
        List<InsuserDTO> insuserDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (insuserDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            //将使用人信息也带出来
            for (InsuserDTO user : insuserDTOS){
                UserPlus userPlus = new UserPlus();
                userPlus.setInsuserDTO(user);
                //去人员表将用户的人员也找出来
                QueryWrapper<JmWorkerEntity> jmWorkerEntityQueryWrapper = new QueryWrapper<>();
                jmWorkerEntityQueryWrapper.eq("wk_no",user.getUsrcode());
                JmWorkerDTO jmWorkerDTO = jmWorkerService.selectOne(jmWorkerEntityQueryWrapper);
                if (jmWorkerDTO!=null && jmWorkerDTO.getWkNo()!=null){
                    userPlus.setJmWorkerDTO(jmWorkerDTO);
                }
                userAndWorkers.add(userPlus);

                userPlus.setPage(dto.getPage());
                userPlus.setPageSize(dto.getPageSize());
                userPlus.setTotal(insuserDTOS.size());
            }
            result.setAll(20000,insuserDTOS,"查找成功");
        }
        return result;
    }

    /**
     * 填写筛选数据
     * @param dto
     * @return
     */
    private QueryWrapper getQueryWrapper(InsuserDTO dto){
        QueryWrapper queryWrapper = new QueryWrapper();
        if (MyUtils.StringIsNull(dto.getUsrcode())){
            queryWrapper.like("usrcode",dto.getUsrcode());
        }
        if (MyUtils.StringIsNull(dto.getUsrname())){
            queryWrapper.like("usrname",dto.getUsrname());
        }
        if (MyUtils.StringIsNull(dto.getUsrnamexa())){
            queryWrapper.like("usrnamexa",dto.getUsrnamexa());
        }
        if (MyUtils.StringIsNull(dto.getUsrnamexb())){
            queryWrapper.like("usrnamexb",dto.getUsrnamexb());
        }
        if (MyUtils.StringIsNull(dto.getUsrshort())){
            queryWrapper.like("usrshort",dto.getUsrshort());
        }
        if (MyUtils.StringIsNull(dto.getOrgcode())){
            queryWrapper.eq("orgcode",dto.getOrgcode());
        }
        if (MyUtils.StringIsNull(dto.getOrgcodeex())){
            queryWrapper.eq("orgcodeex",dto.getOrgcodeex());
        }
        if (MyUtils.StringIsNull(dto.getDoccode())){
            queryWrapper.eq("doccode",dto.getDoccode());
        }
        if (MyUtils.StringIsNull(dto.getGwcode())){
            queryWrapper.eq("gwcode",dto.getGwcode());
        }
        if (MyUtils.StringIsNull(dto.getSexid())){
            queryWrapper.eq("sexid",dto.getSexid());
        }
        if (MyUtils.StringIsNull(dto.getAddress())){
            queryWrapper.eq("address",dto.getAddress());
        }
        if (MyUtils.StringIsNull(dto.getCodeno())){
            queryWrapper.like("codeno",dto.getCodeno());
        }
        if (dto.getUsrattr()!=null){
            queryWrapper.eq("usrattr",dto.getUsrattr());
        }
        if (MyUtils.StringIsNull(dto.getSemail())){
            queryWrapper.like("semail",dto.getSemail());
        }
        if (MyUtils.StringIsNull(dto.getSicard())){
            queryWrapper.like("sicard",dto.getSicard());
        }
        //判定是否管理员
        //状态判定
        if (dto.getAscOrder()!=null){
            queryWrapper.orderByAsc(MyUtils.humpToLine((String) dto.getAscOrder()));
        }
        if (dto.getDescOrder()!=null){
            queryWrapper.orderByDesc(MyUtils.humpToLine((String) dto.getDescOrder()));
        }
        return queryWrapper;
    }
}
