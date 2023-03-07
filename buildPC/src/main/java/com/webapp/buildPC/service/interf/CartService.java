package com.webapp.buildPC.service.interf;

import com.webapp.buildPC.domain.Cart;

public interface CartService {
    void insertToCart(String userID);

    Cart searchCartByUserID(String userID);
    Cart getLastCart(String userID);
}
