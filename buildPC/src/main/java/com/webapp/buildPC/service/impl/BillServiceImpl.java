package com.webapp.buildPC.service.impl;

import com.webapp.buildPC.dao.BillMapper;
import com.webapp.buildPC.domain.Bill;
import com.webapp.buildPC.service.interf.BillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillServiceImpl implements BillService {

    private final BillMapper billMapper ;
    @Override
    public void insertToBill(int total, int status, String paymentMethod, Date payDate, String userID) {
        log.debug("inser new bill");
        billMapper.insertToBill(total,status,paymentMethod,payDate,userID);
    }

    @Override
    public List<Bill> searchBillByUserID(String userId) {
        log.debug("search bill by user");
        return billMapper.searchBillByUserID(userId);
    }

    @Override
    public Bill getLastInsertedBill() {
        log.debug("Get last insert bill");
        return billMapper.getLastInsertedBill();
    }

    @Override
    public void updateStatusBill(int billID, int status) {
        log.debug("change bill status");
        billMapper.updateStatusBill(billID,status);
    }
}
