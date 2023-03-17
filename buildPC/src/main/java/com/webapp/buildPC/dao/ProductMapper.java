package com.webapp.buildPC.dao;

import com.webapp.buildPC.domain.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    void addProduct(String productID, int amount, int total);

    List<Product> getAllProducts();

    Product getProductDetail(String productID);

}
