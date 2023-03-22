package com.webapp.buildPC.service.impl;

import com.webapp.buildPC.dao.BillDetailMapper;
import com.webapp.buildPC.domain.BillDetail;
import com.webapp.buildPC.service.interf.BillDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class BillDetailServiceImpl implements BillDetailService {
    private final BillDetailMapper billDetailMapper ;

    @Override
    public List<BillDetail> findBillDetailByBillID(int billID) {
        log.debug("find bill detail by billID");
        return billDetailMapper.findBillDetailByBillID(billID);
    }

    @Override
    public void updateAmountForComponent(int billID, int componentID, int amount) {
        log.debug("update amount for component");
        billDetailMapper.updateAmountForComponent(billID ,componentID ,amount );
    }

    @Override
    public void insertComponentToBillDetail(int billID, int componentID, int amount) {
        log.debug("insert component to bill detail");
        billDetailMapper.insertComponentToBillDetail(billID, componentID, amount);
    }

    @Override
    public void insertProductToBillDetail(int billID, String productID, int amount) {
        log.debug("insert prodcut to bill detail");
        billDetailMapper.insertProductToBillDetail(billID, productID, amount);
    }
}
