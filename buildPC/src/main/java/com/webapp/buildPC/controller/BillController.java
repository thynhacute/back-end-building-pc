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
    private final PCDetailService pcDetailService;
    private final AddressDetailService addressDetailService;
    private final AddressService addressService;
    private final UserService userService;

    @PostMapping("/checkout")
    public void inserNewBill(@RequestParam String userID, @RequestParam int total, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> responseBillDetail = new HashMap<>();
        Cart cart = cartService.searchCartByUserID(userID);
        if (cart != null) {
            List<CartDetail> cartDetails = cartDetailService.findCartDetailByCartID(cart.getCartID());
            Date now = new Date();
            billService.insertToBill(total, 1, "no", now, userID);
            if (cartDetails.size() > 0) {
                boolean status = false;
                Bill bill = billService.getLastInsertedBill();
                for (CartDetail cartDetailItem :
                        cartDetails) {
                    if (cartDetailItem.getProductID() != null) {
                        billDetailService.insertProductToBillDetail(bill.getBillID(), cartDetailItem.getProductID(), cartDetailItem.getAmount());
                        ResponseProductDetail product = productService.getProductDetail(cartDetailItem.getProductID());
                        int amount = product.getAmount() - cartDetailItem.getAmount();
                        productService.updateProductAmount(cartDetailItem.getProductID(), amount);
                        if (amount <= 3) {
                            PushNotificationRequest requestNoti = new PushNotificationRequest();
                            requestNoti.setTitle("Test noti");
                            requestNoti.setTopic("Notification when amount <= 3");
                            requestNoti.setMessage("The amount of " + product.getProductID() + " only left " + amount);
                            requestNoti.setToken("eesG9_KMS5yI5fu7xhQ7Li:APA91bGDWfeGaNENlqjDIAB3a8Zf_4svwlhCbbuy8Okn4Z_G9Eig-Tq9xE90PVMcYaxTODZRAZb4D7JEQ84ta_v-UjVnxpSawwJGDkbUhaUnDrRTAjTNN1JxrpkgX9dtG77l3Lr3UC1-");
                            pushNotificationService.sendPushNotificationToToken(requestNoti);
                        }
                        cartDetailService.deleteProduct(cartDetailItem.getCartID(), cartDetailItem.getProductID());
                        status = true;
                    } else if (cartDetailItem.getComponentID() != 0) {
                        billDetailService.insertComponentToBillDetail(bill.getBillID(), cartDetailItem.getComponentID(), cartDetailItem.getAmount());
                        Component component = componentService.getComponentDetail(cartDetailItem.getComponentID());
                        int amount = component.getAmount() - cartDetailItem.getAmount();
                        componentService.updateAmountForComponent(cartDetailItem.getComponentID(), amount);
                        if (amount <= 3) {
                            PushNotificationRequest requestNoti = new PushNotificationRequest();
                            requestNoti.setTitle("Test noti");
                            requestNoti.setTopic("Notification when amount <= 3");
                            requestNoti.setMessage("The amount of " + component.getComponentName() + " only left " + amount);
                            requestNoti.setToken("eesG9_KMS5yI5fu7xhQ7Li:APA91bGDWfeGaNENlqjDIAB3a8Zf_4svwlhCbbuy8Okn4Z_G9Eig-Tq9xE90PVMcYaxTODZRAZb4D7JEQ84ta_v-UjVnxpSawwJGDkbUhaUnDrRTAjTNN1JxrpkgX9dtG77l3Lr3UC1-");
                            pushNotificationService.sendPushNotificationToToken(requestNoti);
                        }
                        cartDetailService.deleteComponent(cartDetailItem.getCartID(), cartDetailItem.getComponentID());
                        status = true;
                    }
                }
                if (status) cartService.deteleCartByUser(userID);
            }
        } else if (cart == null) {
            responseBillDetail.put("Status", "cart is null, cannot checkout");
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), responseBillDetail);
        }
    }

    @PostMapping("/finishCheckout")
    public void changeStatus(@RequestBody CartGetByUserID userIDparam) {
        String userID = userIDparam.getUserID();
        List<Bill> bill = billService.searchBillByUserID(userID);
        for (Bill billList :
                bill) {
            if (billList.getStatus() == 1) {
                billService.updateStatusBill(billList.getBillID(), 2);
            }
        }
    }

    @GetMapping("/getBill")
    public void getBillDetailByUserStatus1(@RequestParam String userID, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> responseBillDetail = new HashMap<>();
        List<Bill> bills = billService.searchBillByUserID(userID);
        User user = userService.findUserByID(userID);
        int billID = 0;
        if (bills.size() > 0) {
            List<ResponeProductImg> products = new ArrayList<>();
            List<ComponentRespone> componentRespones = new ArrayList<>();
            List<Address> addressList = new ArrayList<>();
            int amount = 0;
            double total = 0;
            Date billDate = null;
            String payMentMethod = null;
            for (Bill billItem :
                    bills) {
                if (billItem.getStatus() == 1) {
                    payMentMethod = billItem.getPaymentMethod();
                    billDate = billItem.getPayDate();
                    billID = billItem.getBillID();
                    List<BillDetail> billDetails = billDetailService.findBillDetailByBillID(billItem.getBillID());
                    for (BillDetail billDetailItems :
                            billDetails) {
                        if (billDetailItems.getProductID() != null) {
                            List<String> productDetail = new ArrayList<>();
                            List<PCDetail> pcDetails = pcDetailService.getPCDetailByProductID(billDetailItems.getProductID());
                            ResponseProductDetail product = productService.getProductDetail(billDetailItems.getProductID());
                            for (PCDetail pcDetail :
                                    pcDetails) {
                                Component component = componentService.getComponentDetail(pcDetail.getComponentID());
                                productDetail.add(component.getCategoryID() + " : " + component.getComponentName());
                            }
                            products.add(new ResponeProductImg(billDetailItems.getProductID(), productDetail, billDetailItems.getAmount(), product.getTotal(), product.getImageProduct()));
                            total = total + billDetailItems.getAmount() * product.getTotal();
                        }
                        if (billDetailItems.getComponentID() > 0) {
                            Component componentDetail = componentService.getComponentDetail(billDetailItems.getComponentID());
                            componentRespones.add(new ComponentRespone(billDetailItems.getComponentID(), componentDetail.getComponentName(), componentDetail.getPrice(), billDetailItems.getAmount(), componentDetail.getImage()));
                            total = total + billDetailItems.getAmount() * componentDetail.getPrice();
                        }
                        amount = amount + 1;
                    }
                }
            }
            String defaultAddress = null;
            List<AddressDetail> addressDetails = addressDetailService.getListAddressByUser(userID);
            for (AddressDetail addressDetailItem :
                    addressDetails) {
                if (addressDetailItem.getDefaultAddress() != null) {
                    defaultAddress = addressDetailItem.getDefaultAddress();
                }
                if (addressDetailItem.getAddressID() > 0) {
                    Address address = addressService.getAddressByAddressID(addressDetailItem.getAddressID());
                    addressList.add(new Address(addressDetailItem.getAddressID(), address.getProvice(), address.getDistrict(), address.getWard(), address.getApartmentNumber(), address.getCodeArea()));
                }
            }
            String billDateString = null;
            if (billDate != null) {
                Date date = billDate;
                long timestamp = date.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                billDateString = dateFormat.format(date);
            } else {
                billDateString = null;
            }
            AddressRespone addressRespone = new AddressRespone(defaultAddress, addressList);
            responseBillDetail.put("UserID", userID);
            responseBillDetail.put("BillID", billID);
            responseBillDetail.put("Gmail", user.getEmail());
            responseBillDetail.put("PhoneNumber", user.getPhone());
            responseBillDetail.put("ProductID", products);
            responseBillDetail.put("Component", componentRespones);
            responseBillDetail.put("Address", addressRespone);
            responseBillDetail.put("Amount", amount);
            responseBillDetail.put("Total", total);
            responseBillDetail.put("payDate", billDateString);
            responseBillDetail.put("payMentMethod", payMentMethod);
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), responseBillDetail);
        }
    }

    @GetMapping("/getBillByStatusCompleted")
    public void getBillCompleted(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Bill> bills = billService.getBillStatus(2);
        Map<String, Object> responseBillDetail = new HashMap<>();
        List<BillResponeByStatus> billResponesList = new ArrayList<>();
        List<ResponeProductImg> products = new ArrayList<>();
        List<ComponentRespone> componentRespones = new ArrayList<>();
        int amount = 0;
        double total = 0;
        Date billDate = null;
        int billAmount = 0;
        if (bills.size() > 0) {
            for (Bill billsItem :
                    bills) {
                billDate = billsItem.getPayDate();
                List<BillDetail> billDetails = billDetailService.findBillDetailByBillID(billsItem.getBillID());
                for (BillDetail billDetailItems :
                        billDetails) {
                    if (billDetailItems.getProductID() != null) {
                        List<String> productDetail = new ArrayList<>();
                        List<PCDetail> pcDetails = pcDetailService.getPCDetailByProductID(billDetailItems.getProductID());
                        ResponseProductDetail product = productService.getProductDetail(billDetailItems.getProductID());
                        for (PCDetail pcDetail :
                                pcDetails) {
                            Component component = componentService.getComponentDetail(pcDetail.getComponentID());
                            productDetail.add(component.getCategoryID() + " : " + component.getComponentName());
                        }
                        products.add(new ResponeProductImg(billDetailItems.getProductID(), productDetail, billDetailItems.getAmount(), product.getTotal(), product.getImageProduct()));
                        total = total + billDetailItems.getAmount() * product.getTotal();
                    }
                    if (billDetailItems.getComponentID() > 0) {
                        Component componentDetail = componentService.getComponentDetail(billDetailItems.getComponentID());
                        componentRespones.add(new ComponentRespone(billDetailItems.getComponentID(), componentDetail.getComponentName(), componentDetail.getPrice(), billDetailItems.getAmount(), componentDetail.getImage()));
                        total = total + billDetailItems.getAmount() * componentDetail.getPrice();
                    }
                    amount = amount + 1;
                }
                String billDateString = null;
                if (billDate != null) {
                    Date date = billDate;
                    long timestamp = date.getTime();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    billDateString = dateFormat.format(date);
                } else {
                    billDateString = null;
                }
                User user = userService.findUserByID(billsItem.getUserID());
                billResponesList.add(new BillResponeByStatus(billsItem.getUserID(), billsItem.getBillID(), user.getEmail(), user.getPhone(), componentRespones, products, total, amount, billsItem.getPaymentMethod(), billDateString));
                billAmount = billAmount + 1;
            }
            responseBillDetail.put("status", "CompletedBill");
            responseBillDetail.put("billHaveStatus2", billAmount);
            responseBillDetail.put("billDetail", billResponesList);
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), responseBillDetail);
        }
    }
}
