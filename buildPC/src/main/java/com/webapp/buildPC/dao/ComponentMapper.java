package com.webapp.buildPC.dao;

import com.webapp.buildPC.domain.Component;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ComponentMapper {

    List<Component> getAllComponent();

    List<Component> getComponentByName(String componentName);

    List<Component> getComponentByBrand(String brandID);

    void addComponent(Component component);

    void editComponent(Component component);

    Component getComponentDetail(int componentID);

    List<Component> customComponentByAttribute(String attributeID, String categoryID);

    void updateAmountForComponent(int componentID,int amount);

    List<Component> getComponentListByCategoryTypeID(String categoryTypeID);

    List<Component> getComponentByCategoryID(String categoryID);

}
