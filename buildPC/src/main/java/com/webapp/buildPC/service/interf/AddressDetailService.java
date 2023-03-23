package com.webapp.buildPC.service.interf;

import com.webapp.buildPC.domain.AddressDetail;

import java.util.List;

public interface AddressDetailService {
    List<AddressDetail> getListAddressByUser (String userID);
}
