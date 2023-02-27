package com.webapp.buildPC.service.interf;

import com.webapp.buildPC.domain.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ComponentService {

    List<Component> getAllComponent();

    List<Component> getComponentByName(String componentName);

    List<Component> getComponentByBrand(String brandID);

    Component addComponent(String componentName, int price, int amount, MultipartFile avatar, String description, int brandID, String categoryID) throws IOException;

}
