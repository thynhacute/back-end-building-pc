package com.webapp.buildPC.service.interf;

import com.webapp.buildPC.domain.CartDetail;

import java.util.List;

public interface CartDetailService {
    void insertComponentCartDetail(int cartID, String productID, int componentID, int amount);
    List<CartDetail> findCartDetailByCartID(int cartID);
    void updateAmountForProduct(int cartID,String productID,int amount);
    void updateAmountForComponent(int cartID, int componentID,int amount);
//    void addToCartDetail(String productID,int componentID,int amount);
//    List<CartDetail> getAllCartDetail();
    CartDetail getLastCartDetail(String productID,int componentID,int amount);

    void deleteComponent(int cartID,int componentID);
}
