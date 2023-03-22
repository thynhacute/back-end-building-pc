package com.webapp.buildPC.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapp.buildPC.domain.*;
import com.webapp.buildPC.domain.Transaction.*;
import com.webapp.buildPC.service.impl.PushNotificationService;
import com.webapp.buildPC.service.interf.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MimeTypeUtils;
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
    private final PushNotificationService pushNotificationService;
    private final ProductService productService;

    @PostMapping("/checkout")
    public void inserNewBill(@RequestParam String userID,@RequestParam int total, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> responseBillDetail = new HashMap<>();
        Cart cart = cartService.searchCartByUserID(userID);
        if(cart!=null){
            List<CartDetail> cartDetails = cartDetailService.findCartDetailByCartID(cart.getCartID());
                Date now = new Date();
                billService.insertToBill(total,1,"no",now,userID);
            if(cartDetails.size() > 0){
                boolean status = false;
                Bill bill = billService.getLastInsertedBill();
                for (CartDetail cartDetailItem:
                        cartDetails) {
                    if(cartDetailItem.getProductID()!=null){
                        billDetailService.insertProductToBillDetail(bill.getBillID(), cartDetailItem.getProductID(),cartDetailItem.getAmount());
                        ResponseProductDetail product = productService.getProductDetail(cartDetailItem.getProductID());
                        int amount = product.getAmount() - cartDetailItem.getAmount();
                        productService.updateProductAmount(cartDetailItem.getProductID(),amount);
                        if(amount <= 3){
                            PushNotificationRequest requestNoti = new PushNotificationRequest();
                            requestNoti.setTitle("Test noti");
                            requestNoti.setTopic("Notification when amount <= 3");
                            requestNoti.setMessage("The amount of " + product.getProductID() +" only left " +amount);
                            requestNoti.setToken("eesG9_KMS5yI5fu7xhQ7Li:APA91bGDWfeGaNENlqjDIAB3a8Zf_4svwlhCbbuy8Okn4Z_G9Eig-Tq9xE90PVMcYaxTODZRAZb4D7JEQ84ta_v-UjVnxpSawwJGDkbUhaUnDrRTAjTNN1JxrpkgX9dtG77l3Lr3UC1-");
                            pushNotificationService.sendPushNotificationToToken(requestNoti);
                        }
                        cartDetailService.deleteProduct(cartDetailItem.getCartID(),cartDetailItem.getProductID());
                        status = true;
                    } else if (cartDetailItem.getComponentID()!=0) {
                        billDetailService.insertComponentToBillDetail(bill.getBillID(), cartDetailItem.getComponentID(),cartDetailItem.getAmount());
                        Component component = componentService.getComponentDetail(cartDetailItem.getComponentID());
                        int amount = component.getAmount() - cartDetailItem.getAmount();
                        componentService.updateAmountForComponent(cartDetailItem.getComponentID(), amount);
                        if(amount <= 3){
                            PushNotificationRequest requestNoti = new PushNotificationRequest();
                            requestNoti.setTitle("Test noti");
                            requestNoti.setTopic("Notification when amount <= 3");
                            requestNoti.setMessage("The amount of " + component.getComponentName() +" only left " +amount);
                            requestNoti.setToken("eesG9_KMS5yI5fu7xhQ7Li:APA91bGDWfeGaNENlqjDIAB3a8Zf_4svwlhCbbuy8Okn4Z_G9Eig-Tq9xE90PVMcYaxTODZRAZb4D7JEQ84ta_v-UjVnxpSawwJGDkbUhaUnDrRTAjTNN1JxrpkgX9dtG77l3Lr3UC1-");
                            pushNotificationService.sendPushNotificationToToken(requestNoti);
                        }
                        cartDetailService.deleteComponent(cartDetailItem.getCartID(),cartDetailItem.getComponentID());
                        status = true;
                    }
                }
                if(status) cartService.deteleCartByUser(userID);
            }
        }else if(cart == null){
            responseBillDetail.put("Status","cart is null, cannot checkout");
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), responseBillDetail);
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
        Bill bill1 = billService.getLastInsertedBill();
        if(bill1!=null){
            List<BillDetail> billDetails = billDetailService.findBillDetailByBillID(bill1.getBillID());
            for (BillDetail billDetailItem:
                 billDetails) {
                Component component = componentService.getComponentDetail(billDetailItem.getComponentID());
                int amount = component.getAmount() - billDetailItem.getAmount();
                componentService.updateAmountForComponent(billDetailItem.getComponentID(), amount);
                if(amount <= 3){
                    PushNotificationRequest request = new PushNotificationRequest();
                    request.setTitle("Test noti");
                    request.setTopic("Notification when amount <= 3");
                    request.setMessage("The amount of " + component.getComponentName() +" only left " +amount);
                    request.setToken("eesG9_KMS5yI5fu7xhQ7Li:APA91bGDWfeGaNENlqjDIAB3a8Zf_4svwlhCbbuy8Okn4Z_G9Eig-Tq9xE90PVMcYaxTODZRAZb4D7JEQ84ta_v-UjVnxpSawwJGDkbUhaUnDrRTAjTNN1JxrpkgX9dtG77l3Lr3UC1-");
                    pushNotificationService.sendPushNotificationToToken(request);
                }
            }
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
