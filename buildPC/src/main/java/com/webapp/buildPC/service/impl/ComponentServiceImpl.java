package com.webapp.buildPC.service.impl;

import com.webapp.buildPC.dao.ComponentMapper;
import com.webapp.buildPC.domain.Component;
import com.webapp.buildPC.service.interf.ComponentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComponentServiceImpl implements ComponentService {

    private final ComponentMapper componentMapper;
    private final UploadFileServiceImpl uploadFile;

    @Override
    public List<Component> getAllComponent() {
        log.debug("Show the list of Components");
        return componentMapper.getAllComponent();
    }

    @Override
    public List<Component> getComponentByName(String componentName) {
        log.debug("Get the list component by name");
        return componentMapper.getComponentByName(componentName);
    }

    @Override
    public List<Component> getComponentByBrand(String brandID) {
        log.debug("Get the list component by brand");
        return componentMapper.getComponentByBrand(brandID);
    }

    @Override
    public Component addComponent(String componentName, int price, int amount, MultipartFile avatar, String description, int brandID, String categoryID) throws IOException {
        log.debug("add new component' information");
        Component component = new Component();
        component.setComponentName(componentName);
        component.setPrice(price);
        component.setAmount(amount);
        component.setImage(uploadFile.uploadImage(avatar));
        component.setDescription(description);
        component.setBrandID(brandID);
        component.setCategoryID(categoryID);
        component.setStatus(1);
        componentMapper.addComponent(component);
        return component;
    }
}
