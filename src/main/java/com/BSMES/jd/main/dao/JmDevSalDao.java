package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.JmDevSalDTO;
import com.BSMES.jd.main.entity.JmDevSalEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JmDevSalDao extends BaseDao<JmDevSalEntity> {

    //批量新增
    public void saveJmDevSals(List<JmDevSalDTO> dtos);

    //修改
    public void editJmDevSal(JmDevSalDTO dto);

}
