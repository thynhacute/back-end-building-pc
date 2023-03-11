package com.webapp.buildPC.service.interf;

import com.webapp.buildPC.domain.Bill;

import java.util.Date;
import java.util.List;

public interface BillService {
    void insertToBill(int total, int status, String paymentMethod, Date payDate, String userID);

    List<Bill> searchBillByUserID(String userId);
    Bill getLastInsertedBill();
}
