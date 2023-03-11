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
    public void insertToBillDetail(int billID, String productID, int componentID, int amount) {
        log.debug("inser to bill detail");
        billDetailMapper.insertToBillDetail(billID,productID,componentID,amount);
    }

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
}
