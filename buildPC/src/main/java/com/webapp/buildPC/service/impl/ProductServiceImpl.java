package com.webapp.buildPC.service.impl;

import com.webapp.buildPC.dao.ComponentMapper;
import com.webapp.buildPC.dao.PCDetailMapper;
import com.webapp.buildPC.dao.ProductMapper;
import com.webapp.buildPC.domain.Component;
import com.webapp.buildPC.domain.PCDetail;
import com.webapp.buildPC.domain.Product;
import com.webapp.buildPC.domain.Transaction.RequestCustomPC;
import com.webapp.buildPC.domain.Transaction.ResponseProductDetail;
import com.webapp.buildPC.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final PCDetailMapper pcDetailMapper;

    private final ComponentMapper componentMapper;


    @Override
    @Transactional
    public void addProduct(RequestCustomPC requestCustomPC) {
        String productID = requestCustomPC.getProductID();
        List<String> listComponent = requestCustomPC.getListComponent();
        int amount = requestCustomPC.getAmount();
        int total = requestCustomPC.getTotal();

        // Add the product to the Product table
        productMapper.addProduct(productID, amount, total);

        // Loop through the ListComponent and add each component to the PCDetail table
        for (String componentID : listComponent) {
            pcDetailMapper.addListPCDetail(productID, componentID);
        }
    }

    @Override
    public List<ResponseProductDetail> getAllProduct() {
        List<Product> productList = productMapper.getAllProducts();
        List<ResponseProductDetail> productDtoList = new ArrayList<>();
        for (Product product : productList) {
            List<PCDetail> pcDetailList = pcDetailMapper.getPCDetailByProductID(product.getProductID());
            List<Component> componentDtoList = new ArrayList<>();
            for (PCDetail pcDetail : pcDetailList) {
                Component component = componentMapper.getComponentDetail(pcDetail.getComponentID());
//                Component componentDto = new Component(component.getComponentID(), component.getComponentName(), component.getPrice(), component.getAmount(), component.getImage(), component.getDescription(), component.getBrandID(), component.getCategoryID(), component.getFeedbackID(),component.getStatus());
                componentDtoList.add(component);
            }
            ResponseProductDetail productDto = new ResponseProductDetail(product.getProductID(), componentDtoList, product.getAmount(), product.getTotal());
            productDtoList.add(productDto);
        }
        return productDtoList;
    }

    @Override
    public ResponseProductDetail getProductDetail(String productID) {
        Product product = productMapper.getProductDetail(productID);
            List<PCDetail> pcDetailList = pcDetailMapper.getPCDetailByProductID(product.getProductID());
            List<Component> componentDtoList = new ArrayList<>();
            for (PCDetail pcDetail : pcDetailList) {
                Component component = componentMapper.getComponentDetail(pcDetail.getComponentID());
                componentDtoList.add(component);
            }
            ResponseProductDetail productDto = new ResponseProductDetail(product.getProductID(), componentDtoList, product.getAmount(), product.getTotal());
        return productDto;
    }
}
