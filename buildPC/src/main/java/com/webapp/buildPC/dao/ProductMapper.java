package com.webapp.buildPC.dao;

import com.webapp.buildPC.domain.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    void addProduct(String productID, int amount, int total, String userID);

    List<Product> getAllProducts();

    Product getProductDetail(String productID);

    List<Product> getProductOfUser(String userID);

    void removeProduct(String productID);

    void updateProduct(String productID, int amount, int total, String userID);
    void updateProductAmount(String productID,int amount);
}
