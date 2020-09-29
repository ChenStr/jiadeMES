package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmXjTfDao;
import com.BSMES.jd.main.dto.JmMouldDTO;
import com.BSMES.jd.main.dto.JmXjTfDTO;
import com.BSMES.jd.main.entity.JmMouldEntity;
import com.BSMES.jd.main.entity.JmXjTfEntity;
import com.BSMES.jd.main.service.JmXjTfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JmXjTfServiceImpl extends BaseServiceImpl<JmXjTfDao , JmXjTfEntity , JmXjTfDTO> implements JmXjTfService {
    @Override
    public void beforeInsert(JmXjTfDTO dto) {

    }

    @Override
    public void beforEedit(JmXjTfDTO dto) {

    }

    @Override
    public CommonReturn getXjTf(JmXjTfDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto);
        List<JmXjTfDTO> xjTfs = this.select(data);
        if(xjTfs.isEmpty()){
            result.setAll(20000,xjTfs,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,xjTfs,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveXjTf(JmXjTfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 md_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid()) && dto.getCid()!=null ){
            QueryWrapper<JmXjTfEntity> xjTfQueryWrapper = new QueryWrapper<>();
            xjTfQueryWrapper.eq("sid",dto.getSid());
            xjTfQueryWrapper.eq("cid",dto.getCid());
            JmXjTfDTO xjTf = this.selectOne(xjTfQueryWrapper);
            //判断 usrcode 是否重复
            if (xjTf==null || xjTf.getSid()==null){
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
    public CommonReturn editXjTf(JmXjTfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 md_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid()) && dto.getCid()!=null ){
            //获取原先的人员属性值
            QueryWrapper<JmXjTfEntity> jmXjTfQueryWrapper = new QueryWrapper<>();
            jmXjTfQueryWrapper.eq("sid",dto.getSid());
            jmXjTfQueryWrapper.eq("cid",dto.getCid());
            JmXjTfDTO xjTf = this.selectOne(jmXjTfQueryWrapper);
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
    public CommonReturn delXjTf(List<String> sids, List<Integer> cids) {
        CommonReturn result = new CommonReturn();
        //判断长度是否相等
        if (sids!=null && cids!=null && sids.size()!=0 && cids.size()!=0 && sids.size()==cids.size()){
            for (int i = 0 ; i < sids.size() ; i++){
                QueryWrapper<JmXjTfEntity> xjTfQueryWrapper = new QueryWrapper<>();
                xjTfQueryWrapper.eq("sid",sids.get(i));
                xjTfQueryWrapper.eq("cid",cids.get(i));
                try{
                    this.remove(xjTfQueryWrapper);
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
    public CommonReturn getXjTfPage(JmXjTfDTO dto, QueryWrapper queryWrapper) {
        CommonReturn result = new CommonReturn();
        List<JmXjTfDTO> jmXjTfDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        if (jmXjTfDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmXjTfDTOS,"查找成功");
        }
        return result;
    }
}
