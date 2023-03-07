package com.webapp.buildPC.service.impl;

import com.webapp.buildPC.dao.CartMapper;
import com.webapp.buildPC.domain.Cart;
import com.webapp.buildPC.domain.CartDetail;
import com.webapp.buildPC.service.interf.CartDetailService;
import com.webapp.buildPC.service.interf.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartMapper cartMapper;
    @Override
    public void insertToCart(String userID) {
        log.debug("inser a new line to cart");
        cartMapper.insertToCart(userID);
    }

    @Override
    public Cart searchCartByUserID(String userID) {
        log.debug("search by userID");
        Cart cart = cartMapper.searchCartByUserID(userID);
        if(cart == null){
            return null;
        }
        return cart;
    }

    @Override
    public Cart getLastCart(String userID) {
        log.debug("get the last insert cart");
        return cartMapper.getLastCart(userID);
    }
}
