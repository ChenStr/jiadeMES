package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.JmMoMfDao;
import com.BSMES.jd.main.dto.*;
import com.BSMES.jd.main.dto.erp.ErpMfMoDTO;
import com.BSMES.jd.main.dto.erp.ErpTfBomDTO;
import com.BSMES.jd.main.dto.erp.ErpTfMoDTO;
import com.BSMES.jd.main.entity.*;
import com.BSMES.jd.main.service.*;
import com.BSMES.jd.main.service.erp.ErpMfMoService;
import com.BSMES.jd.main.service.erp.ErpTfBomService;
import com.BSMES.jd.main.service.erp.ErpTfMoService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class JmMoMfServiceImpl extends BaseServiceImpl<JmMoMfDao, JmMoMfEntity, JmMoMfDTO> implements JmMoMfService {

    @Autowired
    InsorgService insorgService;

    @Autowired
    JmPrdtService jmPrdtService;

    @Autowired
    JmPrdtutService jmPrdtutService;

    @Autowired
    InssysvarService inssysvarService;

    @Autowired
    JmJobService jmJobService;

    @Autowired
    JmBomMfService jmBomMfService;

    @Autowired
    JmBomTfService jmBomTfService;

    @Autowired
    JmBsDictionaryService jmBsDictionaryService;

    @Autowired
    ErpMfMoService erpMfMoService;

    @Autowired
    ErpTfMoService erpTfMoService;

    @Autowired
    ErpTfBomService erpTfBomService;

    @Autowired
    JmMoMfDao jmMoMfDao;

    @Override
    public void beforeInsert(JmMoMfDTO dto) {
        QueryWrapper<JmBsDictionaryEntity> jmBsDictionaryEntityQueryWrapper = new QueryWrapper<>();
        jmBsDictionaryEntityQueryWrapper.eq("id","DIS20201030011");
        JmBsDictionaryDTO jmBsDictionaryDTO = jmBsDictionaryService.selectOne(jmBsDictionaryEntityQueryWrapper);
        dto.setAstRelease(1);
        if (dto.getHpdate()==null){
            dto.setHpdate(new Date());
        }
        if (dto.getModitime()==null){
            dto.setModitime(new Date());
        }
        dto.setState(Integer.valueOf(jmBsDictionaryDTO.getCode()));
        dto.setSbuid("MO");
    }

    @Override
    public void beforEedit(JmMoMfDTO dto) {

    }

    @DS("master")
    @Override
    public CommonReturn getMoMf(ResultType dto) {
        CommonReturn result = new CommonReturn();
        if(dto.getAscOrder()!=null){
            dto.setAscOrder(MyUtils.humpToLine(dto.getAscOrder().toString()));
        }
        if (dto.getDescOrder()!=null){
            dto.setDescOrder(MyUtils.humpToLine(dto.getDescOrder().toString()));
        }

        List<JmMoMfMore> moMfs = jmMoMfDao.getMoMfMore(dto);
        if(moMfs.isEmpty()){

            //查询其中的原料信息
            for (JmMoMfMore more : moMfs){
                QueryWrapper<JmBomMfEntity> jmBomMfEntityQueryWrapper = new QueryWrapper<>();
                jmBomMfEntityQueryWrapper.eq("prd_no",more.getJmMoMfDTO().getPrdNo());
                JmBomMfDTO jmBomMf = jmBomMfService.selectOne(jmBomMfEntityQueryWrapper);
                if (jmBomMf==null && jmBomMf.getBomNo()==null){
                    break;
                }

                QueryWrapper<JmBomTfEntity> jmBomTfEntityQueryWrapper = new QueryWrapper<>();
                jmBomTfEntityQueryWrapper.eq("bom_no",jmBomMf.getBomNo());
                List<JmBomTfDTO> jmBomTfDTOS = jmBomTfService.select(jmBomTfEntityQueryWrapper);

                if (jmBomTfDTOS==null && jmBomTfDTOS.size()<=0){
                    break;
                }

                List<String> prdNos = new ArrayList<>();
                jmBomTfDTOS.stream().forEach(T->prdNos.add(T.getPrdNo()));
                QueryWrapper<JmPrdtEntity> jmPrdtEntityQueryWrapper = new QueryWrapper<>();
                jmPrdtEntityQueryWrapper.in("prd_no",prdNos);
                List<JmPrdtDTO> jmPrdtDTOS = jmPrdtService.select(jmPrdtEntityQueryWrapper);

                more.setPrdts(jmPrdtDTOS);
            }

            result.setAll(20000,moMfs,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,moMfs,"查找成功");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn getMoNo(ResultType dto) {
        CommonReturn result = new CommonReturn();
        try{
            if(dto.getAscOrder()!=null){
                dto.setAscOrder(MyUtils.humpToLine(dto.getAscOrder().toString()));
            }
            if (dto.getDescOrder()!=null){
                dto.setDescOrder(MyUtils.humpToLine(dto.getDescOrder().toString()));
            }
            List<MoNoSave> moNoSaves = jmMoMfDao.getMoNo(dto);
            result.setAll(20000,moNoSaves,"操作成功");
        }catch (Exception e){
            result.setAll(40000,null,"操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn saveMoMf(JmMoMfDTO dto) {
        CommonReturn result = new CommonReturn();
        dto.setSid(this.getKey("JmMoMf","sid",inssysvarService,dto));
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid()) && MyUtils.StringIsNull(dto.getSorg()) && MyUtils.StringIsNull(dto.getPrdNo()) && dto.getQty()!=null && dto.getEndDd()!=null){
            QueryWrapper<JmMoMfEntity> moMfQueryWrapper = new QueryWrapper<>();
            //判断是否有相同的制令单号了
            moMfQueryWrapper.eq("sid",dto.getSid());
            JmMoMfDTO moMf = this.selectOne(moMfQueryWrapper);
            //判断 sid 是否重复
            if (moMf==null || moMf.getSid()==null){
                this.insert(dto);
                //在erp表中添加
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
                ErpMfMoDTO erpMfMoDTO = new ErpMfMoDTO();
                erpMfMoDTO.setMO_NO(dto.getSid());
                erpMfMoDTO.setMO_DD(dateFormat.format(dto.getHpdate()));
                erpMfMoDTO.setSTA_DD(dateFormat.format(dto.getHpdate()));
                erpMfMoDTO.setEND_DD(dateFormat.format(dto.getEndDd()));
                erpMfMoDTO.setMRP_NO(dto.getPrdNo());
                erpMfMoDTO.setQTY(dto.getQty());
                erpMfMoDTO.setDEP(dto.getSorg());
                erpMfMoDTO.setCLOSE_ID(dto.getState().toString());
                erpMfMoDTO.setUSR(dto.getSmake());
                erpMfMoDTO.setCHK_MAN(dto.getChkMan());
                erpMfMoDTO.setCLS_DATE(dateFormat.format(dto.getHpdate()));
                erpMfMoDTO.setSYS_DATE(dateFormat.format(dto.getHpdate()));
                //根据货品号找出货品所在的仓库
                QueryWrapper<JmPrdtEntity> prdtEntityQueryWrapper = new QueryWrapper<>();
                prdtEntityQueryWrapper.eq("prd_no",dto.getPrdNo());
                JmPrdtDTO jmPrdtDTO = jmPrdtService.selectOne(prdtEntityQueryWrapper);
                erpMfMoDTO.setWH(jmPrdtDTO.getWh());
                erpMfMoService.saveMfMo(erpMfMoDTO);
                //添加子表
                QueryWrapper<JmBomMfEntity> bomMfEntityQueryWrapper = new QueryWrapper<>();
                bomMfEntityQueryWrapper.eq("prd_no",dto.getPrdNo());
                JmBomMfDTO erpMfBomDTO = jmBomMfService.selectOne(bomMfEntityQueryWrapper);
                QueryWrapper<JmBomTfEntity> bomTfEntityQueryWrapper = new QueryWrapper<>();
                bomTfEntityQueryWrapper.eq("bom_no",erpMfBomDTO.getBomNo());
                List<JmBomTfDTO> list = jmBomTfService.select(bomTfEntityQueryWrapper);
                List<ErpTfMoDTO> dtos = new ArrayList<>();
                for(JmBomTfDTO item : list){
                    ErpTfMoDTO erpTfMoDTO = new ErpTfMoDTO();
                    erpTfMoDTO.setMO_NO(dto.getSid());
                    erpTfMoDTO.setITM(item.getItm());
                    erpTfMoDTO.setPRD_NO(item.getPrdNo());
                    //查询原材料名称
                    QueryWrapper<JmPrdtEntity> jmPrdtEntityQueryWrapper = new QueryWrapper<>();
                    jmPrdtEntityQueryWrapper.eq("prd_no",item.getPrdNo());
                    JmPrdtDTO jmPrdtDTO1 = jmPrdtService.selectOne(prdtEntityQueryWrapper);

                    erpTfMoDTO.setPRD_NAME(jmPrdtDTO1.getName());
                    erpTfMoDTO.setWH(item.getWh());
                    erpTfMoDTO.setUNIT(item.getUnit());
                    BigDecimal sum = erpMfMoDTO.getQTY().multiply(item.getQty());
                    erpTfMoDTO.setQTY_RSV(sum);
                    erpTfMoDTO.setEST_ITM(item.getItm());
                    erpTfMoDTO.setPRE_ITM(item.getItm());
                    erpTfMoDTO.setQTY_STD(item.getQty());
                    dtos.add(erpTfMoDTO);
                }
                erpTfMoService.saveTfMos(dtos);
                result.setAll(20000,null,"操作成功");
            }else{
                result.setAll(10001,null,"制令单号已经存在，不能新增!");
            }
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn editMoMf(JmMoMfDTO dto) {
        CommonReturn result = new CommonReturn();
        //判断dto是否为空 判断dto的 wk_no 是否有值
        if (dto!=null && MyUtils.StringIsNull(dto.getSid())){
            //获取原先的人员属性值
            QueryWrapper<JmMoMfEntity> moMfQueryWrapper = new QueryWrapper<>();
            moMfQueryWrapper.eq("sid",dto.getSid());
            JmMoMfDTO moMf = this.selectOne(moMfQueryWrapper);
            //查看该调度单下是否有计划分派
//            QueryWrapper jobQueryWrapper = new QueryWrapper();
//            jobQueryWrapper.eq("sid",dto.getSid());
//            List<JmJobDTO> jmJobs = jmJobService.select(jobQueryWrapper);
//            if (jmJobs==null || jmJobs.size()==0){
                //设置用户不能操作的属性
                try{
                    this.edit(dto);
                    result.setAll(20000,null,"操作成功");
                }catch (Exception e){
                    result.setAll(10001,null,"操作失败");
                }
//            }else{
//                //强制终止调度单
//                dto.setState(12);
//                moMf.setState(12);
//                //将其生产计划单的状态也改为12
//                QueryWrapper<JmJobEntity> jmJobEntityQueryWrapper = new QueryWrapper<>();
//                jmJobEntityQueryWrapper.eq("sid",dto.getSid());
//                List<JmJobDTO> jmJobDTOS = jmJobService.select(jmJobEntityQueryWrapper);
//                for (JmJobDTO jmJobDTO : jmJobDTOS){
//                    jmJobDTO.setState("12");
//                    jmJobService.editJob(jmJobDTO);
//                }
//                this.edit(moMf);
//                result.setAll(20000,null,"终止生产成功");
//            }
            ErpMfMoDTO erpMfMoDTO = new ErpMfMoDTO();
            erpMfMoDTO.setMO_NO(dto.getSid());
            if (dto.getQty()!=null){
                erpMfMoDTO.setQTY(dto.getQty());
            }
            if (dto.getState()!=null){
                erpMfMoDTO.setCLOSE_ID(dto.getState().toString());
            }

            ErpTfMoDTO erpTfMoDTO = new ErpTfMoDTO();
            erpTfMoDTO.setMO_NO(dto.getSid());
            erpTfMoDTO.setQTY_RSV(dto.getQty());
            erpTfMoDTO.setQTY_STD(dto.getQty());

            erpMfMoService.editMfMo(erpMfMoDTO);
            erpTfMoService.editTfMo(erpTfMoDTO);
        }else{
            result.setAll(10001,null,"参数错误");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn delMoMf(List<String> sids) {
        CommonReturn result = new CommonReturn();
        QueryWrapper<JmMoMfEntity> moMfQueryWrapper = new QueryWrapper<>();
        moMfQueryWrapper.in("sid",sids);
        //查看该调度单下面是否有作业计划单
        QueryWrapper jobQueryWrapper = new QueryWrapper();
        jobQueryWrapper.in("sid",sids);
        List<JmJobDTO> jmJobs = jmJobService.select(jobQueryWrapper);
        if (jmJobs==null || jmJobs.size()==0){
            try{
                this.remove(moMfQueryWrapper);
                //erp表联动删除
                erpMfMoService.delMfMo(sids);
                result.setAll(20000,null,"操作成功");
            }catch (Exception e) {
                result.setAll(10001, null, "操作失败");
            }
        }else{
            result.setAll(40000,null,"该调度单下已经有计划分派了，不能删除");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn getMoMfPage(ResultType dto) {
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
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        List<JmMoMfMore> jmMoMfMores = (List<JmMoMfMore>) this.getMoMf(dto).getData();

        for (JmMoMfMore more : jmMoMfMores){
            QueryWrapper<JmBomMfEntity> jmBomMfEntityQueryWrapper = new QueryWrapper<>();
            jmBomMfEntityQueryWrapper.eq("prd_no",more.getJmMoMfDTO().getPrdNo());
            JmBomMfDTO jmBomMf = jmBomMfService.selectOne(jmBomMfEntityQueryWrapper);
            if (jmBomMf==null && jmBomMf.getBomNo()==null){
                break;
            }

            QueryWrapper<JmBomTfEntity> jmBomTfEntityQueryWrapper = new QueryWrapper<>();
            jmBomTfEntityQueryWrapper.eq("bom_no",jmBomMf.getBomNo());
            List<JmBomTfDTO> jmBomTfDTOS = jmBomTfService.select(jmBomTfEntityQueryWrapper);

            if (jmBomTfDTOS==null && jmBomTfDTOS.size()<=0){
                break;
            }

            List<String> prdNos = new ArrayList<>();
            jmBomTfDTOS.stream().forEach(T->prdNos.add(T.getPrdNo()));
            if (prdNos!=null && prdNos.size()>0){
                QueryWrapper<JmPrdtEntity> jmPrdtEntityQueryWrapper = new QueryWrapper<>();
                jmPrdtEntityQueryWrapper.in("prd_no",prdNos);
                List<JmPrdtDTO> jmPrdtDTOS = jmPrdtService.select(jmPrdtEntityQueryWrapper);
                more.setPrdts(jmPrdtDTOS);
            }
        }

        PageInfo jmMoMfMorePageInfo = new PageInfo<JmMoMfMore>(jmMoMfMores);

        result.setAll(20000,jmMoMfMorePageInfo,"操作成功");
        return result;
    }

    /**
     * 筛选条件
     * @param dto
     * @return
     */
    private QueryWrapper getQueryWrapper(JmMoMfDTO dto){

        if(dto.getAscOrder()==null && dto.getDescOrder()==null){
            dto.setDescOrder("state");
        }

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
        if (dto.getState()!=null){
            queryWrapper.eq("state",dto.getState());
        }
        if (MyUtils.StringIsNull(dto.getPrdName())){
            queryWrapper.like("prd_name",dto.getPrdName());
        }
        if (dto.getBegDd()!=null){
            queryWrapper.ge("hpdate",dto.getBegDd());
        }
        if(dto.getEndDd()!=null){
            queryWrapper.le("hpdate",dto.getEndDd());
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
