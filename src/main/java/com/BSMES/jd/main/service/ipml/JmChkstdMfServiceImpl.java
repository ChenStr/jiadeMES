package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmChkstdMfDao;
import com.BSMES.jd.main.dto.InsorgDTO;
import com.BSMES.jd.main.dto.JmChkstd;
import com.BSMES.jd.main.dto.JmChkstdMfDTO;
import com.BSMES.jd.main.entity.InsorgEntity;
import com.BSMES.jd.main.entity.JmChkstdMfEntity;
import com.BSMES.jd.main.entity.JmChkstdTfEntity;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.main.service.JmChkstdMfService;
import com.BSMES.jd.main.service.JmChkstdTfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class JmChkstdMfServiceImpl extends BaseServiceImpl<JmChkstdMfDao , JmChkstdMfEntity , JmChkstdMfDTO> implements JmChkstdMfService {

    @Autowired
    InssysvarService inssysvarService;

    @Autowired
    JmChkstdTfService jmChkstdTfService;

    @Override
    public void beforeInsert(JmChkstdMfDTO dto) {

    }

    @Override
    public void beforEedit(JmChkstdMfDTO dto) {

    }

    @Override
    public CommonReturn getChkstdMf(JmChkstdMfDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto,true);
        List<JmChkstdMfDTO> dtos = this.select(data);
        if(dtos.isEmpty()){
            result.setAll(20000,dtos,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,dtos,"查找成功");
        }
        return result;
    }

    @Transactional
    @Override
    public CommonReturn saveChkstd(JmChkstd dto) {
        CommonReturn result = new CommonReturn();
        Boolean flag = true;
        String sid = null;

        //首先判断是添加还是修改
        if (dto.getJmChkstdMfDTO()!=null && dto.getJmChkstdMfDTO().getChkstdNo()!=null){
            flag=false;
            sid = dto.getJmChkstdMfDTO().getChkstdNo();
            this.edit(dto.getJmChkstdMfDTO());
        }else{
            sid = this.getKey("JmChkstd","chkstd_no",inssysvarService,dto.getJmChkstdMfDTO());
            dto.getJmChkstdMfDTO().setChkstdNo(sid);
            this.insert(dto.getJmChkstdMfDTO());
        }

        try{

            //如果是编辑的话先进行删除
            if (flag==false){
                //删除所有的原始数据
                QueryWrapper<JmChkstdTfEntity> jmChkstdTfEntityQueryWrapper = new QueryWrapper<>();
                jmChkstdTfEntityQueryWrapper.eq("chkstd_no",sid);
                jmChkstdTfService.remove(jmChkstdTfEntityQueryWrapper);
            }
            //将新的数据新增进去
            jmChkstdTfService.saveChkstdTfs(dto.getJmChkstdTfDTOS());
            result.setAll(20000,null,"");
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CommonReturn editChkstdMf(JmChkstdMfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getChkstdNo())){
            //获取原先的用户属性值
            QueryWrapper<JmChkstdMfEntity> jmChkstdMfEntityQueryWrapper = new QueryWrapper<>();
            jmChkstdMfEntityQueryWrapper.eq("chkstd_no",dto.getChkstdNo());
            JmChkstdMfDTO chkstdMfDTO = this.selectOne(jmChkstdMfEntityQueryWrapper);
            //设置用户不能操作的属性
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
    public CommonReturn delChkstdMf(List<String> chkstdNos) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmChkstdMfEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("chkstd_no",chkstdNos);
        try{
            this.remove(queryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getChkstdMfPage(JmChkstdMfDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        IPage<JmChkstdMfDTO> jmChkstdMfDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmChkstdMfDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmChkstdMfDTOS,"查找成功");
        }
        return result;
    }


}
