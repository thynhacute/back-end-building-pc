package com.webapp.buildPC.service.interf;

import com.webapp.buildPC.domain.PCDetail;

import java.util.List;

public interface PCDetailService {
    List<PCDetail> getPCDetailByProductID(String productID);
}
