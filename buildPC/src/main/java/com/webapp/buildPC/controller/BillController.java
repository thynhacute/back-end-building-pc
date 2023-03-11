package com.webapp.buildPC.controller;

import com.webapp.buildPC.domain.*;
import com.webapp.buildPC.domain.Transaction.ResponeNewInserCart;
import com.webapp.buildPC.service.interf.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/bill")
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;
    private final BillDetailService billDetailService;
    private final CartService cartService ;
    private final CartDetailService cartDetailService;
    private final ComponentService componentService;
    private final BrandService brandService;
    private final CategoryService categoryService;
    @PostMapping("/add")
    public void inserNewBill(@RequestBody String userID){
        List<Bill> billFound = billService.searchBillByUserID(userID);
        Map<String, Object> responseBillDetail = new HashMap<>();
        List<ResponeNewInserCart> detailCart = new ArrayList<>();
        boolean check = false;
        if(billFound!=null) {
            for (Bill listbillFound: billFound
             ){
                if(listbillFound.getStatus() ==  1){
                    check = true;
                    List<BillDetail> billDetail = billDetailService.findBillDetailByBillID(listbillFound.getBillID());
                    Cart cartUser = cartService.searchCartByUserID(userID);
                    if(cartUser!=null){
                        List<CartDetail> cartDetailsUser = cartDetailService.findCartDetailByCartID(cartUser.getCartID());
                        if(cartDetailsUser!=null){
                            for (CartDetail cart:
                                    cartDetailsUser) {
                                if(!billDetail.contains(cart)){
                                    billDetailService.insertToBillDetail(listbillFound.getBillID(), cart.getProductID(),cart.getComponentID(),cart.getAmount());
                                }else{
                                    for (BillDetail billDetai:
                                            billDetail) {
                                        if(cart.getComponentID() == billDetai.getComponentID()){
                                            billDetailService.updateAmountForComponent(listbillFound.getBillID(),cart.getComponentID(),cart.getAmount());
                                        }
//                                        if(cart.getProductID().equalsIgnoreCase(billDetai.getProductID()){
//
//                                        }

                                    }
                                }
                            }
                        }
                    }
                    int total = 0;
                    List<BillDetail> listBillDetailUpdated = billDetailService.findBillDetailByBillID(listbillFound.getBillID());
                    for (BillDetail billDetailList:
                            listBillDetailUpdated) {
                        if(billDetailList.getComponentID() != 0){
                            Component component = componentService.getComponentDetail(billDetailList.getComponentID());
                            int amount = component.getAmount() - billDetailList.getAmount();
                            componentService.updateAmountForComponent(billDetailList.getComponentID(),amount);
                            String brand = brandService.findBrandByID(component.getBrandID()).getBrandName();
                            String category = categoryService.getCategoryByCategoryID(component.getCategoryID()).getCategoryName();
                            int price = billDetailList.getAmount() * component.getPrice();
                            detailCart.add(new ResponeNewInserCart(billDetailList.getComponentID(),component.getComponentName(),price,billDetailList.getAmount(),component.getDescription(),brand,category,component.getImage(),component.getStatus()));
                            total = total + price;
                        }
//                        if(billDetailList.getProductID() !=  null)
                    }

                    responseBillDetail.put("billID",listbillFound.getBillID());
                    responseBillDetail.put("userID",listbillFound.getUserID());
                    responseBillDetail.put("total",total);
                    responseBillDetail.put("status",listbillFound.getStatus());
                    responseBillDetail.put("billDetail",detailCart);
                }
            }
        }
        if(check == false || billFound == null){
            Cart cart = cartService.searchCartByUserID(userID);
            if(cart!=null){
                int total = 0;
                List<CartDetail> cartDetails =  cartDetailService.findCartDetailByCartID(cart.getCartID());
                for (CartDetail carts:
                        cartDetails) {
                    if(carts.getComponentID()!=0){
                        Component component = componentService.getComponentDetail(carts.getComponentID());
//                        total =
                    }
//                    if(carts.getProductID()!=null)
                }
            }
//            billService.insertToBill();
        }
    }
}
