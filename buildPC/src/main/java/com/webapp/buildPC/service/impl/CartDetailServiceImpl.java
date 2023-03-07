package com.webapp.buildPC.service.impl;

import com.webapp.buildPC.dao.CartDetailMapper;
import com.webapp.buildPC.domain.CartDetail;
import com.webapp.buildPC.service.interf.CartDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class CartDetailServiceImpl implements CartDetailService {
    private final CartDetailMapper CartDetailMapper;
    @Override
    public void insertComponentCartDetail(int cartID, String productID, int componentID, int amount) {
        CartDetailMapper.insertComponentCartDetail(cartID, productID, componentID, amount);
        log.debug("insert cart to cart detail");
    }

    @Override
    public List<CartDetail> findCartDetailByCartID(int cartID) {
        log.debug("find cart detail by cartID");
        return CartDetailMapper.findCartDetailByCartID(cartID);
    }

    @Override
    public void updateAmountForProduct(int cartID, String productID,int amount) {
        log.debug("update amount for product");
        CartDetailMapper.updateAmountForProduct(cartID, productID,amount);
    }

    @Override
    public void updateAmountForComponent(int cartID, int componentID,int amount) {
        log.debug("update amount for component");
        CartDetailMapper.updateAmountForComponent(cartID, componentID,amount);
    }

//
//
//    @Override
//    public void addToCartDetail(String productID, int componentID, int amount) {
//        log.debug("Add new line in cartdetetail");
//        CartDetail cartDetail = new CartDetail(productID, componentID, amount);
//        CartDetailMapper.addToCartDetail(cartDetail);
//    }
//
//    @Override
//    public List<CartDetail> getAllCartDetail() {
//        log.debug("Get the list of all cart detail");
//        return CartDetailMapper.getAllCartDetail();
//    }
//
    @Override
    public CartDetail getLastCartDetail(String productID, int componentID, int amount) {
        log.debug("Get the last cart detail");
        return CartDetailMapper.getLastCartDetail(new CartDetail(productID,componentID,amount));
    }

    @Override
    public void deleteComponent(int cartID, int componentID) {
        log.debug("Delete component");
        CartDetailMapper.deleteComponent(cartID, componentID);
    }

}
