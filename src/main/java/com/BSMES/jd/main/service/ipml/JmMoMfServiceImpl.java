package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmMoMfDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.entity.InsorgEntity;
import com.BSMES.jd.main.entity.JmMoMfEntity;
import com.BSMES.jd.main.entity.JmPrdtEntity;
import com.BSMES.jd.main.service.InsorgService;
import com.BSMES.jd.main.service.InssysvarService;
import com.BSMES.jd.main.service.JmMoMfService;
import com.BSMES.jd.main.service.JmPrdtService;
import com.BSMES.jd.tools.my.MyUtils;
import com.alibaba.druid.sql.visitor.functions.Char;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JmMoMfServiceImpl extends BaseServiceImpl<JmMoMfDao , JmMoMfEntity , JmMoMfDTO > implements JmMoMfService {

    @Autowired
    InsorgService insorgService;

    @Autowired
    JmPrdtService jmPrdtService;

    @Autowired
    InssysvarService inssysvarService;

    @Override
    public void beforeInsert(JmMoMfDTO dto) {
        dto.setAstRelease(1);
    }

    @Override
    public void beforEedit(JmMoMfDTO dto) {

    }

    @Override
    public CommonReturn getMoMf(JmMoMfDTO dto) {
        CommonReturn result = new CommonReturn();
        Map<String,Object> data = MyUtils.objectToMap(dto);
        List<JmMoMfDTO> moMfs = this.select(data);
        List<JmMoMfMore> mores = new ArrayList<>();
        if (moMfs!=null){
            for (int i = 0 ; i < moMfs.size() ; i++){
                JmMoMfMore more = new JmMoMfMore();
                BeanUtils.copyProperties(moMfs.get(i), more);
                //查询部门信息
                QueryWrapper<InsorgEntity> insorgQueryWrapper = new QueryWrapper<>();
                insorgQueryWrapper.eq("orgcode",moMfs.get(i).getSorg());
                InsorgDTO sorg = insorgService.selectOne(insorgQueryWrapper);
                more.setInsorg(sorg);
                //查询产品信息
                QueryWrapper<JmPrdtEntity> prdtQueryWrapper = new QueryWrapper<>();
                prdtQueryWrapper.eq("prd_no",moMfs.get(i).getPrdNo());
                JmPrdtDTO prdt = jmPrdtService.selectOne(prdtQueryWrapper);
                more.setJmPrdt(prdt);

                mores.add(more);
            }
        }
        if(mores.isEmpty()){
            result.setAll(20000,mores,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,mores,"查找成功");
        }
        return result;
    }

    @Override
    public CommonReturn saveMoMf(JmMoMfDTO dto) {
        CommonReturn result = new CommonReturn();
        dto.setSid(this.getKey("MESB110H"));
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid()) && MyUtils.StringIsNull(dto.getSorg()) && MyUtils.StringIsNull(dto.getPrdNo()) && dto.getQty()!=null && dto.getEndDd()!=null){
            QueryWrapper<JmMoMfEntity> moMfQueryWrapper = new QueryWrapper<>();
            //判断是否有相同的制令单号了
            moMfQueryWrapper.eq("sid",dto.getSid());
            JmMoMfDTO moMf = this.selectOne(moMfQueryWrapper);
            //判断 sid 是否重复
            if (moMf==null || moMf.getSid()==null){
                this.insert(dto);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"制令单号已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @Override
    public CommonReturn editMoMf(JmMoMfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            //获取原先的人员属性值
            QueryWrapper<JmMoMfEntity> moMfQueryWrapper = new QueryWrapper<>();
            moMfQueryWrapper.eq("sid",dto.getSid());
            JmMoMfDTO moMf = this.selectOne(moMfQueryWrapper);
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
    public CommonReturn delMoMf(List<String> sids) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmMoMfEntity> moMfQueryWrapper = new QueryWrapper<>();
        moMfQueryWrapper.in("sid",sids);
        try{
            this.remove(moMfQueryWrapper);
            result.setAll(20000,null,"操作成功");
        }catch (Exception e) {
            result.setAll(20000, null, "操作失败");
        }
        return result;
    }

    @Override
    public CommonReturn getMoMfPage(JmMoMfDTO dto) {
        CommonReturn result = new CommonReturn();
        QueryWrapper queryWrapper = getQueryWrapper(dto);
        List<JmMoMfDTO> jmMoMfDTOS = this.selectPage(dto.getPage(),dto.getPageSize(),queryWrapper);
        List<JmMoMfMore> mores = new ArrayList<>();
        if (jmMoMfDTOS==null){
            result.setAll(10001,null,"参数错误");
        }else{
            //改变数据格式
            for (int i = 0 ; i < jmMoMfDTOS.size() ; i++){
                JmMoMfMore more = new JmMoMfMore();
                BeanUtils.copyProperties(jmMoMfDTOS.get(i), more);
                //查询部门信息
                QueryWrapper<InsorgEntity> insorgQueryWrapper = new QueryWrapper<>();
                insorgQueryWrapper.eq("orgcode",jmMoMfDTOS.get(i).getSorg());
                InsorgDTO sorg = insorgService.selectOne(insorgQueryWrapper);
                more.setInsorg(sorg);
                //查询产品信息
                QueryWrapper<JmPrdtEntity> prdtQueryWrapper = new QueryWrapper<>();
                prdtQueryWrapper.eq("prd_no",jmMoMfDTOS.get(i).getPrdNo());
                JmPrdtDTO prdt = jmPrdtService.selectOne(prdtQueryWrapper);
                more.setJmPrdt(prdt);
                //加入总页数
                more.setTotal(jmMoMfDTOS.size());
                more.setPage(dto.getPage());
                more.setPageSize(dto.getPageSize());

                mores.add(more);
            }

            result.setAll(20000,mores,"查找成功");
        }
        return result;
    }

    /**
     * 筛选条件
     * @param dto
     * @return
     */
    private QueryWrapper getQueryWrapper(JmMoMfDTO dto){
        QueryWrapper queryWrapper = new QueryWrapper();
        if (MyUtils.StringIsNull(dto.getSid())){
            queryWrapper.eq("sid",dto.getSid());
        }
        if (MyUtils.StringIsNull(dto.getSorg())){
            queryWrapper.eq("sorg",dto.getSorg());
        }
        if (MyUtils.StringIsNull(dto.getPrdNo())){
            queryWrapper.eq("prd_no",dto.getPrdNo());
        }
        if (MyUtils.StringIsNull(dto.getPrdName())){
            queryWrapper.like("prd_name",dto.getPrdName());
        }
        if (dto.getBegDd()!=null && dto.getEndDd()!=null){
            queryWrapper.between("hpdate",dto.getBegDd(),dto.getEndDd());
        }
        return queryWrapper;
    }

    public synchronized String getKey(String val){
        //首先先找到编码规则
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("sname",val);
        String code = inssysvarService.selectOne(queryWrapper).getSbds();
        String string = "";
        //获取括号前的数据{
        Integer place1 = code.indexOf('%');
        if (place1>=0 ){
            String befor = code.substring(0,place1);
            String after = code.substring(place1+1,code.length());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateNowStr = sdf.format(new Date());
            //将年月日分开
            String str[] = dateNowStr.split("-");
            string = string+befor+str[0]+str[1]+str[2];
            //查询数据库里的全部值
            QueryWrapper queryWrapper1 = new QueryWrapper();
            queryWrapper1.likeRight("sid",string);
            queryWrapper1.select("sid");
            List<JmMoMfDTO> list = select(queryWrapper1);
            if (list!=null && list.size()!=0){
                List<Integer> ints = new ArrayList<>();
                //将单号后面的字母转换为数字
                for (int i = 0 ; i < list.size() ; i++){
                    ints.add(Integer.valueOf(list.get(i).getSid().substring(string.length(),list.get(i).getSid().length())));
                }
                ints = ints.stream().sorted((t1,t2)-> t2 - t1).collect(Collectors.toList());
                //返回单号
                string = string + MyUtils.geFourNumber(ints.get(0)+1,after.length());
            }else{
                //返回单号
                string = string + MyUtils.geFourNumber(1,after.length());
            }

            return string;
        }else{
            return code;
        }
    }
}
