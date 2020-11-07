package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmGzstdTfDao;
import com.BSMES.jd.main.dto.JmGzstdMfDTO;
import com.BSMES.jd.main.dto.JmGzstdTfDTO;
import com.BSMES.jd.main.dto.JmMdbfTfDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.JmGzstdMfEntity;
import com.BSMES.jd.main.entity.JmGzstdTfEntity;
import com.BSMES.jd.main.entity.JmMouldEntity;
import com.BSMES.jd.main.service.JmGzstdTfService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JmGzstdTfServiceImpl extends BaseServiceImpl<JmGzstdTfDao, JmGzstdTfEntity, JmGzstdTfDTO> implements JmGzstdTfService {

    @Autowired
    JmGzstdTfDao jmGzstdTfDao;

    @Override
    public void beforeInsert(JmGzstdTfDTO dto) {

    }

    @Override
    public void beforEedit(JmGzstdTfDTO dto) {

    }

    @Override
    public CommonReturn getGzstd(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<JmGzstdTfDTO> jmGzstdTfDTOS = this.select(getQueryWrapper(dto));
        if(jmGzstdTfDTOS.isEmpty()){
            result.setAll(20000,jmGzstdTfDTOS,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,jmGzstdTfDTOS,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveGzstds(List<JmGzstdTfDTO> dtos) {
        CommonReturn result = new CommonReturn();
        try{
            jmGzstdTfDao.insertGzstd(dtos);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CommonReturn editGzstd(JmGzstdTfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断 usrcode 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getGzstdNo())){
            //获取原先的用户属性值
            QueryWrapper<JmGzstdTfEntity> jmGzstdMfEntityQueryWrapper = new QueryWrapper<>();
            jmGzstdMfEntityQueryWrapper.eq("gzstd_no",dto.getGzstdNo());
            JmGzstdTfDTO var = this.selectOne(jmGzstdMfEntityQueryWrapper);
            //设置用户不能操作的属性
            try{
//                this.edit(dto);
                jmGzstdTfDao.editGzstd(dto);
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
    public CommonReturn delGzstd(List<String> gzstdNos, List<Integer> cids) {
        CommonReturn result = new CommonReturn();
        //判断长度是否相等
        if (gzstdNos!=null && cids!=null && gzstdNos.size()!=0 && cids.size()!=0 && gzstdNos.size()==cids.size()){
            for (int i = 0 ; i < gzstdNos.size() ; i++){
                QueryWrapper<JmGzstdTfEntity> jmGzstdTfEntityQueryWrapper = new QueryWrapper<>();
                jmGzstdTfEntityQueryWrapper.eq("gzstd_no",gzstdNos.get(i));
                jmGzstdTfEntityQueryWrapper.eq("cid",cids.get(i));
                try{
                    this.remove(jmGzstdTfEntityQueryWrapper);
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
    public CommonReturn getGzstdPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<JmGzstdTfDTO> jmGzstdTfDTOIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        if (jmGzstdTfDTOIPage==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,jmGzstdTfDTOIPage,"查找成功");
        }
        return result;
    }

    /**
     * 填写筛选数据
     * @param dto
     * @return
     */
    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();

        if (MyUtils.StringIsNull(dto.getSid())){
            queryWrapper.eq("gzstd_no",dto.getSid());
        }

        if (dto.getAscOrder()!=null){
            queryWrapper.orderByAsc(MyUtils.humpToLine((String) dto.getAscOrder()));
        }
        if (dto.getDescOrder()!=null && dto.getAscOrder()==null){
            queryWrapper.orderByDesc(MyUtils.humpToLine((String) dto.getDescOrder()));
        }

        return queryWrapper;
    }
}
