package com.webapp.buildPC.dao;

import com.webapp.buildPC.domain.PCDetail;
import com.webapp.buildPC.domain.Transaction.RequestCustomPC;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PCDetailMapper {

    void addListPCDetail(String productID, String componentID);


    List<PCDetail> getPCDetailByProductID(String productID);
}
