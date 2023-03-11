package com.webapp.buildPC.dao;

import com.webapp.buildPC.domain.BillDetail;

import java.util.List;

public interface BillDetailMapper {
    void insertToBillDetail(int billID,String productID,int componentID,int amount);
    List<BillDetail> findBillDetailByBillID(int billID);
    void updateAmountForComponent (int billID,int componentID,int amount);
}
