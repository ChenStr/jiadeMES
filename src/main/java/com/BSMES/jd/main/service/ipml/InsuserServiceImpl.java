package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.InsuserDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.InsuserEntity;
import com.BSMES.jd.main.entity.JmWorkerEntity;
import com.BSMES.jd.main.service.InsuserService;
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
        Map<String,Object> data = MyUtils.objectToMap(dto,true);
        List<UserAndWorker> userAndWorkers = new ArrayList<>();
        List<InsuserDTO> users = this.select(data);
        if(users.isEmpty()){
            result.setAll(20000,users,"没有查找结果，建议仔细核对查找条件");
        }else{
            //将使用人信息也带出来
            for (InsuserDTO user : users){
                UserAndWorker userAndWorker = new UserAndWorker();
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
        Map<String,Object> data = MyUtils.objectToMap(dto,true);
        List<InsuserDTO> users = this.select(data);
        if(users.isEmpty()){
            result.setAll(20000,users,"没有查找结果，建议仔细核对查找条件");
        }else{

            result.setAll(20000,users,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn login(InsuserDTO dto) {
        CommonReturn result = new CommonReturn();
        UserAndWorker userAndWorker = new UserAndWorker();
        //首先判断是否是工位机登录还是PC端登录
        if(dto!=null && dto.getSicard()!=null){
            //通过卡号将人员与账号信息查出来
            QueryWrapper<InsuserEntity> insuserEntityQueryWrapper = new QueryWrapper<>();
            insuserEntityQueryWrapper.eq("sicard",dto.getSicard());
            InsuserDTO insuserDTO = this.selectOne(insuserEntityQueryWrapper);
            //去人员表将用户的人员也找出来
            QueryWrapper<JmWorkerEntity> jmWorkerEntityQueryWrapper = new QueryWrapper<>();
            jmWorkerEntityQueryWrapper.eq("wk_no",dto.getUsrcode());
            JmWorkerDTO jmWorkerDTO = jmWorkerService.selectOne(jmWorkerEntityQueryWrapper);
            //判断是否有查询到数据
            if (insuserDTO!=null && insuserDTO.getUsrcode()!=null){
                userAndWorker.setInsuserDTO(insuserDTO);
            }else{
                result.setAll(40000,null,"卡号不存在");
                return result;
            }

            if (jmWorkerDTO!=null && jmWorkerDTO.getWkNo()!=null){
                userAndWorker.setJmWorkerDTO(jmWorkerDTO);
            }
            result.setAll(20000,userAndWorker,"登录成功");
        }else if (dto.getUsrcode()!=null && dto.getPswd()!=null){
            //根据这个用户名查询用户
            QueryWrapper<InsuserEntity> insuserEntityQueryWrapper = new QueryWrapper<>();
            insuserEntityQueryWrapper.eq("usrcode",dto.getUsrcode());
            InsuserDTO insuserDTO = this.selectOne(insuserEntityQueryWrapper);
            //判断用户的账户是否存在，密码是否正确
            if (insuserDTO!=null && insuserDTO.getPswd()!=null && PasswordUtils.matches(dto.getPswd(),insuserDTO.getPswd())){
                userAndWorker.setInsuserDTO(insuserDTO);
                //去人员表将用户的人员也找出来
                QueryWrapper<JmWorkerEntity> jmWorkerEntityQueryWrapper = new QueryWrapper<>();
                jmWorkerEntityQueryWrapper.eq("wk_no",dto.getUsrcode());
                JmWorkerDTO jmWorkerDTO = jmWorkerService.selectOne(jmWorkerEntityQueryWrapper);
                if (jmWorkerDTO!=null && jmWorkerDTO.getWkNo()!=null){
                    userAndWorker.setJmWorkerDTO(jmWorkerDTO);
                }
                result.setAll(20000,userAndWorker,"登录成功");
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
        List<UserAndWorker> userAndWorkers = new ArrayList<>();
        QueryWrapper queryWrapper = this.getQueryWrapper(dto);
        List<InsuserDTO> insuserDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (insuserDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            //将使用人信息也带出来
            for (InsuserDTO user : insuserDTOS){
                UserAndWorker userAndWorker = new UserAndWorker();
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
        return queryWrapper;
    }
}
