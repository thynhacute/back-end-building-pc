package com.webapp.buildPC.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapp.buildPC.domain.*;
import com.webapp.buildPC.domain.Transaction.BillRespone;
import com.webapp.buildPC.domain.Transaction.BillResponeByStatus;
import com.webapp.buildPC.domain.Transaction.CartGetByUserID;
import com.webapp.buildPC.domain.Transaction.ResponeNewInserCart;
import com.webapp.buildPC.service.interf.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/bill")
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;
    private final BillDetailService billDetailService;
    private final CartService cartService;
    private final CartDetailService cartDetailService;
    private final ComponentService componentService;
    private final BrandService brandService;
    private final CategoryService categoryService;

    @PostMapping("/checkout")
    public void inserNewBill(@RequestBody CartGetByUserID userIDparam, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userID = userIDparam.getUserID();
        List<Bill> billFound = billService.searchBillByUserID(userID);
        Map<String, Object> responseBillDetail = new HashMap<>();
        List<ResponeNewInserCart> detailBill = new ArrayList<>();
        boolean check = false;
        if (billFound.size() > 0) {
            for (Bill listbillFound : billFound
            ) {
                if (listbillFound.getStatus() == 1) {
                    check = true;
                    List<BillDetail> billDetail = billDetailService.findBillDetailByBillID(listbillFound.getBillID());
                    Cart cartUser = cartService.searchCartByUserID(userID);
                    if (cartUser != null) {
                        List<CartDetail> cartDetailsUser = cartDetailService.findCartDetailByCartID(cartUser.getCartID());
                        if (cartDetailsUser != null) {
                            for (CartDetail cart :
                                    cartDetailsUser) {
                                boolean itemFound = false;
                                for (BillDetail billDetai :
                                        billDetail) {
                                    if (cart.getComponentID() == billDetai.getComponentID()) {
                                        itemFound = true;
                                        billDetailService.updateAmountForComponent(billDetai.getBillID(), billDetai.getComponentID(), cart.getAmount());
                                    }
//                                    if(cart.getProductID().equalsIgnoreCase(billDetai.getProductID()))
                                }
                                if (!itemFound) {
                                    billDetailService.insertToBillDetail(listbillFound.getBillID(), cart.getProductID(), cart.getComponentID(), cart.getAmount());
                                }
                            }
                        }
                    }
                    int total = 0;
                    List<BillDetail> listBillDetailUpdated = billDetailService.findBillDetailByBillID(listbillFound.getBillID());
                    for (BillDetail billDetailList :
                            listBillDetailUpdated) {
                        if (billDetailList.getComponentID() != 0) {
                            Component component = componentService.getComponentDetail(billDetailList.getComponentID());
                            int amount = component.getAmount() - billDetailList.getAmount();
                            componentService.updateAmountForComponent(billDetailList.getComponentID(), amount);
                            String brand = brandService.findBrandByID(component.getBrandID()).getBrandName();
                            String category = categoryService.getCategoryByCategoryID(component.getCategoryID()).getCategoryName();
                            int price = billDetailList.getAmount() * component.getPrice();
                            detailBill.add(new ResponeNewInserCart(billDetailList.getComponentID(), component.getComponentName(), price, billDetailList.getAmount(), component.getDescription(), brand, category, component.getImage(), component.getStatus()));
                            total = total + price;
                        }
//                        if(billDetailList.getProductID() !=  null)
                    }

                    responseBillDetail.put("billID", listbillFound.getBillID());
                    responseBillDetail.put("userID", listbillFound.getUserID());
                    responseBillDetail.put("total", total);
                    responseBillDetail.put("status", listbillFound.getStatus());
                    responseBillDetail.put("billDetail", detailBill);
                    response.setContentType("application/json");
                    new ObjectMapper().writeValue(response.getOutputStream(), responseBillDetail);
                }
            }
        }
        if (check == false || billFound.size() == 0) {
            Cart cart = cartService.searchCartByUserID(userID);
            if (cart != null) {
                int total = 0;
                List<CartDetail> cartDetails = cartDetailService.findCartDetailByCartID(cart.getCartID());
                for (CartDetail carts :
                        cartDetails) {
                    if (carts.getComponentID() != 0) {
                        Component component = componentService.getComponentDetail(carts.getComponentID());
                        total = total + carts.getAmount() * component.getPrice();
                    }
//                    if(carts.getProductID()!=null)
                }
                Calendar calendar = Calendar.getInstance();
                Date currentDate = calendar.getTime();
                billService.insertToBill(total, 1, null, currentDate, userID);
                Bill bill = billService.getLastInsertedBill();
                for (CartDetail carts :
                        cartDetails
                ) {
                    billDetailService.insertToBillDetail(bill.getBillID(), carts.getProductID(), carts.getComponentID(), carts.getAmount());
                    Component component = componentService.getComponentDetail(carts.getComponentID());
                    String brand = brandService.findBrandByID(component.getBrandID()).getBrandName();
                    String category = categoryService.getCategoryByCategoryID(component.getCategoryID()).getCategoryName();
                    int price = carts.getAmount() * component.getPrice();
                    detailBill.add(new ResponeNewInserCart(carts.getComponentID(), component.getComponentName(), price, carts.getAmount(), component.getDescription(), brand, category, component.getImage(), component.getStatus()));
                }
                responseBillDetail.put("billID", bill.getBillID());
                responseBillDetail.put("userID", userID);
                responseBillDetail.put("total", total);
                responseBillDetail.put("status", bill.getStatus());
                responseBillDetail.put("billDetail", detailBill);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), responseBillDetail);
            }


        }
    }

    @PostMapping("/finishCheckout")
    public void changeStatus(@RequestBody CartGetByUserID userIDparam) {
        String userID = userIDparam.getUserID();
        List<Bill> bill = billService.searchBillByUserID(userID);
        boolean checkStatusChange = false;
        for (Bill billList :
                bill) {
            if (billList.getStatus() == 1) {
                billService.updateStatusBill(billList.getBillID(), 2);
                checkStatusChange = true;
            }
        }
        if (checkStatusChange) {
            Cart cart = cartService.searchCartByUserID(userID);
            List<CartDetail> cartDetail = cartDetailService.findCartDetailByCartID(cart.getCartID());
            for (CartDetail cartDetailItem:
                    cartDetail) {
                cartDetailService.deleteComponent(cartDetailItem.getCartID(),cartDetailItem.getComponentID());
            }
            cartService.deteleCartByUser(userID);
        }
    }

    @GetMapping("/getBill")
    public void getBillDetailByUser(@RequestParam String userID, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Bill> billFound = billService.searchBillByUserID(userID);
        Map<String, Object> responseBillDetail = new HashMap<>();
        List<BillRespone> billResponesList = new ArrayList<>();
        int billAmount = 0;
        if (billFound.size() > 0) {
            for (Bill billItem :
                    billFound) {
                List<ResponeNewInserCart> componentDetailList = new ArrayList<>();
                int total = 0;
                int amount = 0;
                List<BillDetail> billDetails = billDetailService.findBillDetailByBillID(billItem.getBillID());
                for (BillDetail billDetailsItem :
                        billDetails) {
                    Component component = componentService.getComponentDetail(billDetailsItem.getComponentID());
                    String brand = brandService.findBrandByID(component.getBrandID()).getBrandName();
                    String category = categoryService.getCategoryByCategoryID(component.getCategoryID()).getCategoryName();
                    componentDetailList.add(new ResponeNewInserCart(billDetailsItem.getComponentID(), component.getComponentName(), component.getPrice(), billDetailsItem.getAmount(), component.getDescription(), brand, category, component.getImage(), component.getStatus()));
                    total = total + billDetailsItem.getAmount() * component.getPrice();
                    amount = amount + billDetailsItem.getAmount();
                }
                if(billItem.getPayDate() != null) {
                    Date date = billItem.getPayDate();
                    long timestamp = date.getTime();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = dateFormat.format(date);
                    billResponesList.add(new BillRespone(billItem.getBillID(), componentDetailList, billItem.getStatus(), total, amount, billItem.getPaymentMethod(), formattedDate));
                }
                else if(billItem.getPayDate() == null){
                    billResponesList.add(new BillRespone(billItem.getBillID(), componentDetailList, billItem.getStatus(), total, amount, billItem.getPaymentMethod(), null));
                }
                billAmount = billAmount + 1;
            }
            responseBillDetail.put("userID",userID);
            responseBillDetail.put("billAmount",billAmount);
            responseBillDetail.put("billDetail",billResponesList);
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), responseBillDetail);
        }
    }
    @GetMapping("/getBillByStatusCompleted")
    public void getBillCompleted(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Bill> bills = billService.getBillStatus(2);
        Map<String, Object> responseBillDetail = new HashMap<>();
        List<BillResponeByStatus> billResponesList = new ArrayList<>();
        int billAmount = 0;
        if(bills.size()>0){
        for (Bill billsItem:
             bills) {
            List<ResponeNewInserCart> componentDetailList = new ArrayList<>();
            int total = 0;
            int amount = 0;
            List<BillDetail> billDetails = billDetailService.findBillDetailByBillID(billsItem.getBillID());
            for (BillDetail billDetailsItem :
                    billDetails) {
                Component component = componentService.getComponentDetail(billDetailsItem.getComponentID());
                String brand = brandService.findBrandByID(component.getBrandID()).getBrandName();
                String category = categoryService.getCategoryByCategoryID(component.getCategoryID()).getCategoryName();
                componentDetailList.add(new ResponeNewInserCart(billDetailsItem.getComponentID(), component.getComponentName(), component.getPrice(), billDetailsItem.getAmount(), component.getDescription(), brand, category, component.getImage(), component.getStatus()));
                total = total + billDetailsItem.getAmount() * component.getPrice();
                amount = amount + billDetailsItem.getAmount();
            }
            if(billsItem.getPayDate() != null) {
                Date date = billsItem.getPayDate();
                long timestamp = date.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = dateFormat.format(date);
                billResponesList.add(new BillResponeByStatus(billsItem.getBillID(), componentDetailList, billsItem.getUserID(), total, amount, billsItem.getPaymentMethod(), formattedDate));
            }
            else if(billsItem.getPayDate() == null){
                billResponesList.add(new BillResponeByStatus(billsItem.getBillID(), componentDetailList, billsItem.getUserID(), total, amount, billsItem.getPaymentMethod(), null));
            }
            billAmount = billAmount + 1;
        }
            responseBillDetail.put("status","FinishCheckOut");
            responseBillDetail.put("billHaveStatus2",billAmount);
            responseBillDetail.put("billDetail",billResponesList);
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), responseBillDetail);
    }}
}
