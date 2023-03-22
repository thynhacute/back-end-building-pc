package com.webapp.buildPC.service.interf;

import com.webapp.buildPC.domain.BillDetail;

import java.util.List;

public interface BillDetailService {
    List<BillDetail> findBillDetailByBillID(int billID);
    void updateAmountForComponent (int billID,int componentID,int amount);
    void insertComponentToBillDetail(int billID,int componentID,int amount);
    void insertProductToBillDetail(int billID,String productID,int amount);
}
