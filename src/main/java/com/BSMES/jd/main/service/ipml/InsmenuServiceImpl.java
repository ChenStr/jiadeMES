package com.BSMES.jd.main.service.ipml;

import com.BSMES.jd.common.dto.CommonReturn;
import com.BSMES.jd.common.service.impl.BaseServiceImpl;
import com.BSMES.jd.main.dao.InsmenuDao;
import com.BSMES.jd.main.dto.InsgwcodeDTO;
import com.BSMES.jd.main.dto.InsmenuDTO;
import com.BSMES.jd.main.dto.ResultType;
import com.BSMES.jd.main.entity.InsmenuEntity;
import com.BSMES.jd.main.service.InsmenuService;
import com.BSMES.jd.tools.my.MyUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsmenuServiceImpl extends BaseServiceImpl<InsmenuDao , InsmenuEntity , InsmenuDTO> implements InsmenuService {
    @Override
    public void beforeInsert(InsmenuDTO dto) {

    }

    @Override
    public void beforEedit(InsmenuDTO dto) {

    }

    @DS("master")
    @Override
    public CommonReturn getInsmenu(ResultType dto) {
        CommonReturn result = new CommonReturn();
        List<InsmenuDTO> insmenuDTOS = this.select(this.getQueryWrapper(dto));
        if(insmenuDTOS.isEmpty()){
            result.setAll(20000,insmenuDTOS,"没有查找结果，建议仔细核对查找条件");
        }else{
            result.setAll(20000,insmenuDTOS,"查找成功");
        }
        return result;
    }

    @DS("master")
    @Override
    public CommonReturn getInsmenuPage(ResultType dto) {
        CommonReturn result = new CommonReturn();
        IPage<InsmenuDTO> insmenuDTOIPage = this.selectPage(dto.getPage(),dto.getPageSize(),this.getQueryWrapper(dto));
        if (insmenuDTOIPage==null){
            result.setAll(10001,null,"参数错误");
        }else{
            result.setAll(20000,insmenuDTOIPage,"查找成功");
        }
        return result;
    }

    private QueryWrapper getQueryWrapper(ResultType dto){
        QueryWrapper queryWrapper = new QueryWrapper();

        queryWrapper.eq("mbtype",2);
        queryWrapper.notIn("menuattr",9);

        if (MyUtils.StringIsNull(dto.getSid())){
            queryWrapper.eq("menuid",dto.getSid());
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
