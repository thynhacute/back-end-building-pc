package com.webapp.buildPC.dao;

import com.webapp.buildPC.domain.BillDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface BillDetailMapper {
    List<BillDetail> findBillDetailByBillID(int billID);
    void updateAmountForComponent (int billID,int componentID,int amount);
    void insertComponentToBillDetail(int billID,int componentID,int amount);
    void insertProductToBillDetail(int billID,String productID,int amount);
}
