package com.BSMES.jd.main.dao;

import com.BSMES.jd.common.dao.BaseDao;
import com.BSMES.jd.main.entity.JmWxIdEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestMapping;

@Mapper
public interface JmWxIdDao extends BaseDao<JmWxIdEntity> {
}
