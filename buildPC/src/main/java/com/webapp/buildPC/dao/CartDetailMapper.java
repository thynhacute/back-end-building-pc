package com.webapp.buildPC.dao;

import com.webapp.buildPC.domain.CartDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartDetailMapper {
    void insertComponentCartDetail(int cartID, String productID, int componentID, int amount);
    List<CartDetail> findCartDetailByCartID(int cartID);

    void updateAmountForProduct(int cartID,String productID,int amount);
    void updateAmountForComponent(int cartID, int componentID,int amount);
//
//    void addToCartDetail(CartDetail cartDetail);
//    List<CartDetail> getAllCartDetail();
    CartDetail getLastCartDetail(CartDetail cartDetail);

    void deleteComponent(int cartID,int componentID);
}
