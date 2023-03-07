package com.webapp.buildPC.dao;

import com.webapp.buildPC.domain.Cart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartMapper {
    void insertToCart(String userID);

    Cart searchCartByUserID(String userID);

    Cart getLastCart(String userID);
}
