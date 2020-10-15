package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmMtdd2TfDao;
import com.BSMES.jd.main.dto.JmMtdd2TfDTO;
import com.BSMES.jd.main.entity.JmMtdd2TfEntity;
import com.BSMES.jd.main.service.JmMtdd2TfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class JmMtdd2TfServiceImpl extends BaseServiceImpl<JmMtdd2TfDao, JmMtdd2TfEntity, JmMtdd2TfDTO> implements JmMtdd2TfService {

    @Override
    public void beforeInsert(JmMtdd2TfDTO dto) {

    }

    @Override
    public void beforEedit(JmMtdd2TfDTO dto) {

    }

    @Override
    public CommonReturn getMtdd2(JmMtdd2TfDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto,true);
        List<JmMtdd2TfDTO> jmMtstd2TfDTOS = this.select(data);
        if(jmMtstd2TfDTOS.isEmpty()){
            result.setAll(20000,jmMtstd2TfDTOS,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,jmMtstd2TfDTOS,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveMtdd2(JmMtdd2TfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            QueryWrapper<JmMtdd2TfEntity> jmMtstd2TfEntityQueryWrapper = new QueryWrapper<>();
            jmMtstd2TfEntityQueryWrapper.eq("sid",dto.getSid());
            JmMtdd2TfDTO jmMtstd2TfDTO = this.selectOne(jmMtstd2TfEntityQueryWrapper);
            //判断 usrcode 是否重复
            if (jmMtstd2TfDTO==null || jmMtstd2TfDTO.getSid()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"设备已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn editMtdd2(JmMtdd2TfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            //获取原先的人员属性值
            QueryWrapper<JmMtdd2TfEntity> jmMtstd2TfEntityQueryWrapper = new QueryWrapper<>();
            jmMtstd2TfEntityQueryWrapper.eq("sids",dto.getSid());
            JmMtdd2TfDTO jmMtstd2TfDTO = this.selectOne(jmMtstd2TfEntityQueryWrapper);
            //设置用户不能操作的属性
            try{
                this.edit(dto);
                result.setAll(20000,null,"操作成功");
            }catch (Exception e){
                result.setAll(10001,null,"操作失败");
                e.printStackTrace();
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn delMtdd2(List<String> sids, List<Integer> cids) {
        CommonReturn result = new CommonReturn();
        //判断长度是否相等
        if (sids!=null && cids!=null && sids.size()!=0 && cids.size()!=0 && sids.size()==cids.size()){
            for (int i = 0 ; i < cids.size() ; i++){
                QueryWrapper<JmMtdd2TfEntity> jmMtstd2TfEntityQueryWrapper = new QueryWrapper<>();
                jmMtstd2TfEntityQueryWrapper.eq("sid",sids.get(i));
                jmMtstd2TfEntityQueryWrapper.eq("cid",cids.get(i));
                try{
                    this.remove(jmMtstd2TfEntityQueryWrapper);
                    result.setAll(20000,null,"操作成功");
                }catch (Exception e) {
                    result.setAll(10001, null, "操作失败");
                    return result;
                }
            }
        }else{
            result.setAll(10001,null,"操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getMtdd2Page(JmMtdd2TfDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        List<JmMtdd2TfDTO> jmMtstd2TfDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmMtstd2TfDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmMtstd2TfDTOS,"查找成功");
        }
        return result;
    }
}
