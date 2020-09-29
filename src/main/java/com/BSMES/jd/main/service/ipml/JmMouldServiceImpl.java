package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmMouldDao;
import com.BSMES.jd.main.dto.JmMouldDTO;
import com.BSMES.jd.main.entity.JmMouldEntity;
import com.BSMES.jd.main.service.JmMouldService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JmMouldServiceImpl extends BaseServiceImpl<JmMouldDao , JmMouldEntity , JmMouldDTO> implements JmMouldService {
    @Override
    public void beforeInsert(JmMouldDTO dto) {

    }

    @Override
    public void beforEedit(JmMouldDTO dto) {

    }

    @Override
    public CommonReturn getMould(JmMouldDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto);
        List<JmMouldDTO> moulds = this.select(data);
        if(moulds.isEmpty()){
            result.setAll(20000,moulds,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,moulds,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveMould(JmMouldDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 md_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getMdNo()) && dto.getTypeid()!=null ){
            QueryWrapper<JmMouldEntity> mouldQueryWrapper = new QueryWrapper<>();
            mouldQueryWrapper.eq("md_no",dto.getMdNo());
            mouldQueryWrapper.eq("typeid",dto.getTypeid());
            JmMouldDTO JmMould = this.selectOne(mouldQueryWrapper);
            //判断 usrcode 是否重复
            if (JmMould==null || JmMould.getMdNo()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"模具号已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn editMould(JmMouldDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 md_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getMdNo()) && dto.getTypeid()!=null ){
            //获取原先的人员属性值
            QueryWrapper<JmMouldEntity> mouldQueryWrapper = new QueryWrapper<>();
            mouldQueryWrapper.eq("md_no",dto.getMdNo());
            mouldQueryWrapper.eq("typeid",dto.getTypeid());
            JmMouldDTO job = this.selectOne(mouldQueryWrapper);
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
    public CommonReturn delMould(List<String> sids, List<Integer> cids) {
        CommonReturn result = new CommonReturn();
        //判断长度是否相等
        if (sids!=null && cids!=null && sids.size()!=0 && cids.size()!=0 && sids.size()==cids.size()){
            for (int i = 0 ; i < sids.size() ; i++){
                QueryWrapper<JmMouldEntity> mouldQueryWrapper = new QueryWrapper<>();
                mouldQueryWrapper.eq("md_no",sids.get(i));
                mouldQueryWrapper.eq("typeid",cids.get(i));
                try{
                    this.remove(mouldQueryWrapper);
                    result.setAll(20000,null,"操作成功");
                }catch (Exception e) {
                    result.setAll(20000, null, "操作失败");
                    return result;
                }
            }

        }else{
            result.setAll(10001,null,"操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getMoMfPage(JmMouldDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        List<JmMouldDTO> jmMouldDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmMouldDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmMouldDTOS,"查找成功");
        }
        return result;
    }
}
