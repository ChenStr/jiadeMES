package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.dto.JmJobDTO;
import com.BSMES.jd.main.dto.JmJobRecBDTO;
import com.BSMES.jd.main.entity.JmJobRecBEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JmJobRecBDao extends BaseDao<JmJobRecBEntity> {

    void updateJobRecB(JmJobRecBDTO jmJobRecB);

}
