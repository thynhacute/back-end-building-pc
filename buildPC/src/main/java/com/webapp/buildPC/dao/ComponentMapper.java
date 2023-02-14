package com.webapp.buildPC.dao;

import com.webapp.buildPC.domain.Component;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ComponentMapper {

    List<Component> getAllComponent();

    List<Component> getComponentByName(String componentName);

    List<Component> getComponentByBrand(String brandID);


}
