package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmBomMfDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.JmBomMfEntity;
import com.BSMES.jd.main.entity.JmBomTfEntity;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.main.service.JmBomMfService;
import com.BSMES.jd.main.service.JmBomTfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Queue;

@Service
public class JmBomMfServiceImpl extends BaseServiceImpl<JmBomMfDao , JmBomMfEntity , JmBomMfDTO> implements JmBomMfService {

    @Autowired
    JmBomMfDao jmBomMfDao;

    @Autowired
    InssysvarService inssysvarService;

    @Autowired
    JmBomTfService jmBomTfService;

    @Override
    public void beforeInsert(JmBomMfDTO dto) {
        dto.setHpdate(new Date());
    }

    @Override
    public void beforEedit(JmBomMfDTO dto) {

    }

    @Override
    public CommonReturn getBomMf(JmBomMfDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto,true);
        List<JmBomMfDTO> bomMf = this.select(data);
        if(bomMf.isEmpty()){
            result.setAll(20000,bomMf,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,bomMf,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn getBomPlus(ResultType dto) {
        CommonReturn result = new CommonReturn();
        try {
            List<BomPlus> bomPluses = jmBomMfDao.getJmBomMfDao(dto);
            result.setAll(20000,bomPluses,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CommonReturn saveBomMf(BomPlus dto) {
        CommonReturn result = new CommonReturn();
        Boolean flag = true;
        String sid = null;
        if (dto.getJmBomMfDTO()!=null && dto.getJmBomMfDTO().getBomNo()!=null){
            flag=false;
            sid = dto.getJmBomMfDTO().getBomNo();
            this.edit(dto.getJmBomMfDTO());
        }else{
            sid = this.getKey("Bom","bom_no",inssysvarService,dto.getJmBomMfDTO());
            dto.getJmBomMfDTO().setBomNo(sid);
            this.insert(dto.getJmBomMfDTO());
        }

        try{
            //如果是编辑的话
            if (flag==false){
                //删除所有原始数据
                QueryWrapper<JmBomTfEntity> jmBomTfEntityQueryWrapper = new QueryWrapper<>();
                jmBomTfEntityQueryWrapper.eq("bom_no",dto.getJmBomMfDTO().getBomNo());
                jmBomTfService.remove(jmBomTfEntityQueryWrapper);
            }
            //将新的数据添加进去
            for (JmBomTfDTO jmBomTfDTO : dto.getJmBomTfDTOS()){
                jmBomTfDTO.setBomNo(sid);
                if (jmBomTfDTO!=null && jmBomTfDTO.getBomNo()!=null){
                    jmBomTfService.saveBomTfs(dto.getJmBomTfDTOS());
                }
            }
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public CommonReturn editBomMf(JmBomMfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getBomNo())){
            //获取原先的人员属性值
            QueryWrapper<JmBomMfEntity> bomMfQueryWrapper = new QueryWrapper<>();
            bomMfQueryWrapper.eq("dev_no",dto.getBomNo());
            JmBomMfDTO bomMf = this.selectOne(bomMfQueryWrapper);
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
    public CommonReturn delBomMf(List<String> bomNos) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmBomMfEntity> bomMfQueryWrapper = new QueryWrapper<>();
        QueryWrapper<JmBomTfEntity> jmBomTfEntityQueryWrapper = new QueryWrapper<>();
        bomMfQueryWrapper.in("bom_no",bomNos);
        jmBomTfEntityQueryWrapper.in("bom_no",bomNos);
        try{
            this.remove(bomMfQueryWrapper);
            jmBomTfService.remove(jmBomTfEntityQueryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(10001, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getBomMfPage(JmBomMfDTO dto) {
        CommonReturn result = new CommonReturn();
        QueryWrapper queryWrapper = this.getQueryWrapper(dto);
        IPage<JmBomMfDTO> jmBomMfDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmBomMfDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmBomMfDTOS,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn getBomPlusPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        if (dto.getDescOrder()==null && dto.getAscOrder()==null){
            dto.setDescOrder("hpdate");
        }
        if (dto.getPage()==null){
            dto.setPage(1);
        }
        if (dto.getPageSize()==null){
            dto.setPageSize(10);
        }
        try{
            PageHelper.startPage(dto.getPage(), dto.getPageSize());
            List<BomPlus> bomPluses = (List<BomPlus>) this.getBomPlus(dto).getData();
            PageInfo pageInfo = new PageInfo<BomPlus>(bomPluses);
            result.setAll(20000,pageInfo,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 筛选条件
     * @param dto
     * @return
     */
    private QueryWrapper getQueryWrapper(JmBomMfDTO dto){
        QueryWrapper queryWrapper = new QueryWrapper();
        return queryWrapper;
    }
}
