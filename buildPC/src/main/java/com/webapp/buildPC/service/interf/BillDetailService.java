package com.webapp.buildPC.service.interf;

import com.webapp.buildPC.domain.BillDetail;

import java.util.List;

public interface BillDetailService {
    void insertToBillDetail(int billID,String productID,int componentID,int amount);
    List<BillDetail> findBillDetailByBillID(int billID);
    void updateAmountForComponent (int billID,int componentID,int amount);
}
