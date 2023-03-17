package com.webapp.buildPC.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapp.buildPC.domain.Cart;
import com.webapp.buildPC.domain.CartDetail;
import com.webapp.buildPC.domain.Component;
import com.webapp.buildPC.domain.ProductAmount;
import com.webapp.buildPC.domain.Transaction.*;
import com.webapp.buildPC.service.interf.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final CartDetailService cartDetailService;
    private final ComponentService componentService;
    private final BrandService brandService;
    private final CategoryService categoryService;
    @PostMapping("/add")
    public void insertComponent(@RequestBody CartParam cartParam, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String userID = cartParam.getUserID();
            String productID = cartParam.getProductID();
            int componentID = cartParam.getComponentID();
            int amount = cartParam.getAmount();
            Cart cart = cartService.searchCartByUserID(userID);
            if (cart == null) {
                cartService.insertToCart(userID);
                Cart cartCreated = cartService.getLastCart(userID);
                cartDetailService.insertComponentCartDetail(cartCreated.getCartID(), productID, componentID, amount);
                Map<String, Object> responseCartDetail = new HashMap<>();
                CartDetail cartDetail = cartDetailService.getLastCartDetail(productID, componentID, amount);
                Component compoDetail = componentService.getComponentDetail(cartDetail.getComponentID());
                String brand = brandService.findBrandByID(compoDetail.getBrandID()).getBrandName();
                String category = categoryService.getCategoryByCategoryID(compoDetail.getCategoryID()).getCategoryName();
                ResponeNewInserCart newInsertCart = new ResponeNewInserCart(compoDetail.getComponentID(), compoDetail.getComponentName(), compoDetail.getPrice(), amount, compoDetail.getDescription(), brand, category,compoDetail.getImage(), compoDetail.getStatus());
                LocalDateTime currentDate = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String formattedDate = currentDate.format(formatter);
                responseCartDetail.put("cartID", cartCreated.getCartID());
                responseCartDetail.put("UserID", userID);
                responseCartDetail.put("productDetail", newInsertCart);
                responseCartDetail.put("Quantity", amount);
                responseCartDetail.put("TotalPrice", amount * compoDetail.getPrice());
                responseCartDetail.put("CreatedCartTime", formattedDate);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), responseCartDetail);
            } else {
                List<CartDetail> cartDetail = cartDetailService.findCartDetailByCartID(cart.getCartID());
                Map<String, Object> responseCartDetail = new HashMap<>();
                boolean check = true;
                if (cartDetail.size() > 0) {
                    for (CartDetail item :
                            cartDetail) {

                        if (productID != null && productID.equalsIgnoreCase(item.getProductID())) {
                            int amountProduct = item.getAmount() + amount;
//                        cartDetailService.updateAmountForProduct(userID, item.getProductID(), amountProduct);
                            //                    Component compoDetail = componentService.getComponentDetail(componentID);
                            //                    amountBoth.put(compoDetail.getComponentName(),amountProduct);
                            check = false;
                        } else if (componentID != 0 && componentID == item.getComponentID()) {
                            int amountComponent = item.getAmount() + amount;
                            cartDetailService.updateAmountForComponent(item.getCartID(), item.getComponentID(), amountComponent);
                            Component compoDetail = componentService.getComponentDetail(componentID);
                            check = false;
                        }
                    }
                }
                if (check) {
                    cartDetailService.insertComponentCartDetail(cart.getCartID(), productID, componentID, amount);
                    if (productID != null) {

                    } else {
                        Component compoDetail = componentService.getComponentDetail(componentID);
                    }
                }

                if (productID != null) {

                }
                Component compoDetail = componentService.getComponentDetail(componentID);
                String brand = brandService.findBrandByID(compoDetail.getBrandID()).getBrandName();
                String category = categoryService.getCategoryByCategoryID(compoDetail.getCategoryID()).getCategoryName();
                ResponeNewInserCart newInsertCart = new ResponeNewInserCart(componentID, compoDetail.getComponentName(), compoDetail.getPrice(), amount, compoDetail.getDescription(), brand, category, compoDetail.getImage(), compoDetail.getStatus());
                List<CartDetail> cartDetailAll = cartDetailService.findCartDetailByCartID(cart.getCartID());
                List<ResponeNewInserCart> detailCart = new ArrayList<>();
                int amoutALl = 0;
                int totallyPrice = 0;
                for (CartDetail item : cartDetailAll) {
                    Component compoDetailAll = componentService.getComponentDetail(item.getComponentID());
                    String brandAll = brandService.findBrandByID(compoDetailAll.getBrandID()).getBrandName();
                    String categoryAll = categoryService.getCategoryByCategoryID(compoDetailAll.getCategoryID()).getCategoryName();
                    detailCart.add(new ResponeNewInserCart(compoDetailAll.getComponentID(), compoDetailAll.getComponentName(), compoDetailAll.getPrice(), item.getAmount(), compoDetailAll.getDescription(), brandAll, categoryAll, compoDetailAll.getImage(),compoDetailAll.getStatus()));
                    amoutALl = amoutALl + item.getAmount();
                    totallyPrice = totallyPrice + (item.getAmount() * compoDetailAll.getPrice());
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                LocalDateTime currentDate = LocalDateTime.now();
                String formattedDate = currentDate.format(formatter);
                responseCartDetail.put("cartID", cart.getCartID());
                responseCartDetail.put("userID", cart.getUserID());
                responseCartDetail.put("NewProductAdded", newInsertCart);
                responseCartDetail.put("AllProductCart", detailCart);
                responseCartDetail.put("TotalAmount", amoutALl);
                responseCartDetail.put("totallyPrice", totallyPrice);
                responseCartDetail.put("UpdateCartTime", formattedDate);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), responseCartDetail);

            }
        }catch(Exception e){
            response.setHeader("error", e.getMessage());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            Map<String, String> error = new HashMap<>();
            error.put("error_message", e.getMessage());
            response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }
    }
    @PostMapping("/addCustom")
    public void insertCustomComponent(@RequestBody ListComponentAddToCart cartParam, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String userID = cartParam.getUserID();
            List<ListComponent> listComponentList = cartParam.getListComponentList();
            Cart cart = cartService.searchCartByUserID(userID);
            Map<String, Object> responseCartDetail = new HashMap<>();
            List<ResponeNewInserCart> newInsertCart = new ArrayList<>();
            if (cart == null) {
                cartService.insertToCart(userID);
                Cart cartCreated = cartService.getLastCart(userID);
                int amount = 0;
                int price = 0;
                if (listComponentList.size() > 0) {
                    for (ListComponent component :
                            listComponentList) {
                        String productID = component.getProductID();
                        int componentID = component.getComponentID();
                        amount = amount + component.getAmount();
                        cartDetailService.insertComponentCartDetail(cartCreated.getCartID(), productID, componentID, amount);
                        CartDetail cartDetail = cartDetailService.getLastCartDetail(productID, componentID, amount);
                        Component compoDetail = componentService.getComponentDetail(cartDetail.getComponentID());
                        price = price + component.getAmount() * compoDetail.getPrice();
                        String brand = brandService.findBrandByID(compoDetail.getBrandID()).getBrandName();
                        String category = categoryService.getCategoryByCategoryID(compoDetail.getCategoryID()).getCategoryName();
                        newInsertCart.add(new ResponeNewInserCart(compoDetail.getComponentID(), compoDetail.getComponentName(), compoDetail.getPrice(), amount, compoDetail.getDescription(), brand, category, compoDetail.getImage(), compoDetail.getStatus()));
                    }
                    LocalDateTime currentDate = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    String formattedDate = currentDate.format(formatter);
                    responseCartDetail.put("cartID", cartCreated.getCartID());
                    responseCartDetail.put("UserID", userID);
                    responseCartDetail.put("productDetail", newInsertCart);
                    responseCartDetail.put("Quantity", amount);
                    responseCartDetail.put("TotalPrice", price);
                    responseCartDetail.put("CreatedCartTime", formattedDate);
                    response.setContentType("application/json");
                    new ObjectMapper().writeValue(response.getOutputStream(), responseCartDetail);
                }
            } else {
                List<CartDetail> cartDetail = cartDetailService.findCartDetailByCartID(cart.getCartID());
                boolean check = true;
                if (cartDetail.size() > 0) {
                    for (ListComponent component :
                            listComponentList) {
                        String productID = component.getProductID();
                        int componentID = component.getComponentID();
                        int amount = component.getAmount();
                        for (CartDetail item :
                                cartDetail) {
                            if (componentID != 0 && componentID == item.getComponentID()) {
                                int amountComponent = item.getAmount() + amount;
                                cartDetailService.updateAmountForComponent(item.getCartID(), item.getComponentID(), amountComponent);
                                check = false;
                            }
                        }
                        if (check) {
                            cartDetailService.insertComponentCartDetail(cart.getCartID(), productID, componentID, amount);
                        }
                        Component compoDetail = componentService.getComponentDetail(componentID);
                        String brand = brandService.findBrandByID(compoDetail.getBrandID()).getBrandName();
                        String category = categoryService.getCategoryByCategoryID(compoDetail.getCategoryID()).getCategoryName();
                        newInsertCart.add(new ResponeNewInserCart(componentID, compoDetail.getComponentName(), compoDetail.getPrice(), amount, compoDetail.getDescription(), brand, category, compoDetail.getImage(), compoDetail.getStatus()));
                    }
                }
                List<CartDetail> cartDetailAll = cartDetailService.findCartDetailByCartID(cart.getCartID());
                List<ResponeNewInserCart> detailCart = new ArrayList<>();
                int amoutALl = 0;
                int totallyPrice = 0;
                for (CartDetail item : cartDetailAll) {
                    Component compoDetailAll = componentService.getComponentDetail(item.getComponentID());
                    String brandAll = brandService.findBrandByID(compoDetailAll.getBrandID()).getBrandName();
                    String categoryAll = categoryService.getCategoryByCategoryID(compoDetailAll.getCategoryID()).getCategoryName();
                    detailCart.add(new ResponeNewInserCart(compoDetailAll.getComponentID(), compoDetailAll.getComponentName(), compoDetailAll.getPrice(), item.getAmount(), compoDetailAll.getDescription(), brandAll, categoryAll, compoDetailAll.getImage(), compoDetailAll.getStatus()));
                    amoutALl = amoutALl + item.getAmount();
                    totallyPrice = totallyPrice + (item.getAmount() * compoDetailAll.getPrice());
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                LocalDateTime currentDate = LocalDateTime.now();
                String formattedDate = currentDate.format(formatter);
                responseCartDetail.put("cartID", cart.getCartID());
                responseCartDetail.put("userID", cart.getUserID());
                responseCartDetail.put("NewProductAdded", newInsertCart);
                responseCartDetail.put("AllProductCart", detailCart);
                responseCartDetail.put("TotalAmount", amoutALl);
                responseCartDetail.put("totallyPrice", totallyPrice);
                responseCartDetail.put("UpdateCartTime", formattedDate);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), responseCartDetail);

            }
        } catch (Exception e) {
            response.setHeader("error", e.getMessage());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            Map<String, String> error = new HashMap<>();
            error.put("error_message", e.getMessage());
            response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }
    }

    @PutMapping("/updateamount")
    public void updateAmount(@RequestBody CartParam updateParam, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userID = updateParam.getUserID();
        int componentID = updateParam.getComponentID();
        String productID = updateParam.getProductID();
        int amount = updateParam.getAmount();
        Cart cart = cartService.searchCartByUserID(userID);
        List<ResponeNewInserCart> detailCart = new ArrayList<>();
        Map<String, Object> responseCartDetail = new HashMap<>();
        if (cart != null) {
            List<CartDetail> listCartDetail = cartDetailService.findCartDetailByCartID(cart.getCartID());
            if (listCartDetail != null) {
                for (CartDetail item : listCartDetail
                ) {
                    if (productID != null && productID.equalsIgnoreCase(item.getProductID())) {
                        cartDetailService.updateAmountForProduct(item.getCartID(), productID, amount);
                    } else if (componentID > 0 && componentID == item.getComponentID()) {
                        cartDetailService.updateAmountForComponent(item.getCartID(), componentID, amount);
                        Component componentDetail = componentService.getComponentDetail(componentID);
                        String brand = brandService.findBrandByID(componentDetail.getBrandID()).getBrandName();
                        String category = categoryService.getCategoryByCategoryID(componentDetail.getCategoryID()).getCategoryName();
                        detailCart.add(new ResponeNewInserCart(componentDetail.getComponentID(), componentDetail.getComponentName(), componentDetail.getPrice(), item.getAmount(), componentDetail.getDescription(), brand, category, componentDetail.getImage(), componentDetail.getStatus()));
                        responseCartDetail.put("cartID", item.getCartID());
                        responseCartDetail.put("UserID", userID);
                        responseCartDetail.put("productWasUpdate", detailCart);
                        responseCartDetail.put("Quantity", amount);
                        responseCartDetail.put("TotalPrice", amount * componentDetail.getPrice());
                        response.setContentType("application/json");
                        new ObjectMapper().writeValue(response.getOutputStream(), responseCartDetail);
                    }
                }
            }
        }
    }

    @PostMapping("/removecomponent")
    public void deleteComponent(@RequestBody DeleteComponentFromCart deleteParam) throws JsonProcessingException {
        String userID = deleteParam.getUserID();
        int componentID = deleteParam.getComponentID();
        Cart cart = cartService.searchCartByUserID(userID);
        List<CartDetail> cartDetail = cartDetailService.findCartDetailByCartID(cart.getCartID());
        if (cartDetail != null) {
            for (CartDetail item :
                    cartDetail) {
                if (componentID == item.getComponentID()) {
                    cartDetailService.deleteComponent(item.getCartID(), componentID);
                }
            }
        }
    }

    @GetMapping("/getcart")
    public void getAllCartByUser(@RequestParam String userID, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cart cart = cartService.searchCartByUserID(userID);
        Map<String, Object> responseCartDetail = new HashMap<>();
        if (cart != null) {
            List<CartDetail> cartDetail = cartDetailService.findCartDetailByCartID(cart.getCartID());
            List<ResponeNewInserCart> detailCart = new ArrayList<>();
            List<ProductAmount> productAmounts = new ArrayList<>();
            int amount = 0;
            int price = 0;
            if (cartDetail.size() > 0) {
                for (CartDetail item :
                        cartDetail) {
                    Component componentDetail = componentService.getComponentDetail(item.getComponentID());
                    String brand = brandService.findBrandByID(componentDetail.getBrandID()).getBrandName();
                    String category = categoryService.getCategoryByCategoryID(componentDetail.getCategoryID()).getCategoryName();
                    detailCart.add(new ResponeNewInserCart(componentDetail.getComponentID(), componentDetail.getComponentName(), componentDetail.getPrice(), item.getAmount(), componentDetail.getDescription(), brand, category, componentDetail.getImage(), componentDetail.getStatus()));
                    amount = amount + item.getAmount();
                    price = price + (item.getAmount() * componentDetail.getPrice());
                    productAmounts.add(new ProductAmount(componentDetail.getComponentName(), item.getAmount(), componentDetail.getPrice(), item.getAmount() * componentDetail.getPrice(), componentDetail.getImage()));
                }
            }
            responseCartDetail.put("cartID", cart.getCartID());
            responseCartDetail.put("UserID", userID);
            responseCartDetail.put("Product", productAmounts);
            responseCartDetail.put("ProductDetail", detailCart);
            responseCartDetail.put("TotalQuantity", amount);
            responseCartDetail.put("TotalPrice", price);
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), responseCartDetail);
        } else if (cart == null) {
            responseCartDetail.put("CartStatus", "No componeent added to cart");
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), responseCartDetail);
        }
    }
}
