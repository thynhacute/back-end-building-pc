package com.webapp.buildPC.service.impl;

import com.webapp.buildPC.dao.ComponentMapper;
import com.webapp.buildPC.domain.Component;
import com.webapp.buildPC.domain.Transaction.RequestComponentByAttribute;
import com.webapp.buildPC.service.interf.ComponentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        try {
            component.setComponentName(componentName);
            component.setPrice(price);
            component.setAmount(amount);
            component.setImage(uploadFile.uploadImage(avatar));
            component.setDescription(description);
            component.setBrandID(brandID);
            component.setCategoryID(categoryID);
            component.setStatus(1);
            componentMapper.addComponent(component);
        }catch (Exception e){
            log.error("The add component error: " + e);
        }
        return getComponentDetail(component.getComponentID());
    }

    @Override
    @Transactional
    public Component editComponent(Component component) throws IOException {
        log.debug("Edit component' information");
        try {
            component.setStatus(1);
            componentMapper.editComponent(component);
        }catch (Exception e){
            log.error("The edit component error: " + e);
        }
        return getComponentDetail(component.getComponentID());
    }

    @Override
    public void updateAmountForComponent(int componentID, int amount) {
        log.debug("Update amount for component");
        componentMapper.updateAmountForComponent(componentID,amount);
    }

    @Override
    public Component getComponentDetail(int componentID) {
        log.debug("Show The Component Detail");
        return componentMapper.getComponentDetail(componentID);
    }

    @Override
    public List<Component> customComponentByAttribute(RequestComponentByAttribute requestComponentByAttribute) {
        log.debug("Show The List component for custom PC");
        return componentMapper.customComponentByAttribute(requestComponentByAttribute.getAttributeID(), requestComponentByAttribute.getCategoryID());
    }
}
