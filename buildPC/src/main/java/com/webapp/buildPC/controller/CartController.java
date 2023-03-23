package com.webapp.buildPC.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapp.buildPC.domain.*;
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
    private final ProductService productService;
    private final PCDetailService pcDetailService;
    @PostMapping("/add")
    public void insertComponent(@RequestBody CartParam cartParam, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String userID = cartParam.getUserID();
            String productID = cartParam.getProductID();
            int componentID = cartParam.getComponentID();
            int amount = cartParam.getAmount();
            Cart cart = cartService.searchCartByUserID(userID);
            if (cart == null) {
                Map<String, Object> responseCartDetail = new HashMap<>();
                cartService.insertToCart(userID);
                Cart cartCreated = cartService.getLastCart(userID);
                if(productID!=null){
                    cartDetailService.insertProductCartDetail(cartCreated.getCartID(),productID,amount);
                }
                else{
                    cartDetailService.insertComponentCartDetail(cartCreated.getCartID(), componentID, amount);
                }
            } else {
                List<CartDetail> cartDetail = cartDetailService.findCartDetailByCartID(cart.getCartID());
                Map<String, Object> responseCartDetail = new HashMap<>();
                boolean check = true;
                if (cartDetail.size() > 0) {
                    for (CartDetail item :
                            cartDetail) {
                        if (productID != null && productID.equalsIgnoreCase(item.getProductID())) {
                            int amountComponent = item.getAmount() + amount;
                            cartDetailService.updateAmountForProduct(item.getCartID(),item.getProductID(),amountComponent);
                            check = false;
                        } else if (componentID != 0 && componentID == item.getComponentID()) {
                            int amountComponent = item.getAmount() + amount;
                            cartDetailService.updateAmountForComponent(item.getCartID(), item.getComponentID(), amountComponent);
                            check = false;
                        }
                    }
                }
                if (check) {
                    if(productID!=null){
                        cartDetailService.insertProductCartDetail(cart.getCartID(),productID,amount);
                    }
                    else{
                        cartDetailService.insertComponentCartDetail(cart.getCartID(), componentID, amount);
                    }
                }
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
                        responseCartDetail.put("cartID", item.getCartID());
                        responseCartDetail.put("UserID", userID);
                        responseCartDetail.put("Quantity", amount);
                        response.setContentType("application/json");
                        new ObjectMapper().writeValue(response.getOutputStream(), responseCartDetail);
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

    @PostMapping("/remove")
    public void deleteComponent(@RequestBody DeleteComponentFromCart deleteParam) throws JsonProcessingException {
        String userID = deleteParam.getUserID();
        int componentID = deleteParam.getComponentID();
        String productID = deleteParam.getProductID();
        Cart cart = cartService.searchCartByUserID(userID);
        List<CartDetail> cartDetail = cartDetailService.findCartDetailByCartID(cart.getCartID());
        if (cartDetail != null) {
            for (CartDetail item :
                    cartDetail) {
                if(productID!=null && productID.equalsIgnoreCase(item.getProductID())){
                    cartDetailService.deleteProduct(item.getCartID(),productID);
                }
                if (componentID!= 0 && componentID == item.getComponentID()) {
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
            List<ResponeProductImg> products = new ArrayList<>();
            List<ComponentRespone> componentRespones = new ArrayList<>();
            int quantity = 0;
            if (cartDetail.size() > 0) {
                for (CartDetail item :
                        cartDetail) {
                    if(item.getProductID()!=null){
                        List<String> productDetail = new ArrayList<>();
                        List<PCDetail> pcDetails = pcDetailService.getPCDetailByProductID(item.getProductID());
                         ResponseProductDetail product = productService.getProductDetail(item.getProductID());
                        for (PCDetail pcDetail:
                                pcDetails) {
                            Component component = componentService.getComponentDetail(pcDetail.getComponentID());
                            productDetail.add(component.getCategoryID() + " : "+ component.getComponentName());
                        }
                        products.add(new ResponeProductImg(item.getProductID(),productDetail, item.getAmount(), product.getTotal(),product.getImageProduct()));

                    }
                    else {
                        Component componentDetail = componentService.getComponentDetail(item.getComponentID());
                        componentRespones.add(new ComponentRespone(item.getComponentID(),componentDetail.getComponentName(),componentDetail.getPrice(),item.getAmount(),componentDetail.getImage()));
                    }
                    quantity = quantity + 1;
                }

            }
            responseCartDetail.put("cartID", cart.getCartID());
            responseCartDetail.put("UserID", userID);
            responseCartDetail.put("product", products);
            responseCartDetail.put("component", componentRespones);
            responseCartDetail.put("totalQuantity", quantity);
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), responseCartDetail);
        } else if (cart == null) {
            responseCartDetail.put("CartStatus", "No componeent added to cart");
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), responseCartDetail);
        }
    }
}
