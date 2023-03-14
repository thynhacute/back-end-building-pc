package com.webapp.buildPC.dao;

import com.webapp.buildPC.domain.Bill;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface BillMapper {
    void insertToBill(int total, int status, String paymentMethod, Date payDate,String userID);
    List<Bill> searchBillByUserID(String userId);

    Bill getLastInsertedBill();

    void updateStatusBill(int billID,int status);
    List<Bill> getBillStatus(int status);
}
