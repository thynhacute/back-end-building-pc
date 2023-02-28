package com.webapp.buildPC.dao;

import com.webapp.buildPC.domain.Attribute;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttributeMapper {

    List<Attribute> getAllAttribute();

}
