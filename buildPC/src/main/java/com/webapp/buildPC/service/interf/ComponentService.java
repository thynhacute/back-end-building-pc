package com.webapp.buildPC.service.interf;

import com.webapp.buildPC.domain.Component;

import java.util.List;

public interface ComponentService {

    List<Component> getAllComponent();

    List<Component> getComponentByName(String componentName);

    List<Component> getComponentByBrand(String brandID);


}
