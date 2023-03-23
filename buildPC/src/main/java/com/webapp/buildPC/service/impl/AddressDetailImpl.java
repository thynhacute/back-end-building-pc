package com.webapp.buildPC.service.impl;

import com.webapp.buildPC.dao.AddressDetailMapper;
import com.webapp.buildPC.domain.AddressDetail;
import com.webapp.buildPC.service.interf.AddressDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class AddressDetailImpl implements AddressDetailService {
    private final AddressDetailMapper addressDetailMapper;
    @Override
    public List<AddressDetail> getListAddressByUser(String userID) {
        log.debug("Get list address by user");
        return addressDetailMapper.getListAddressByUser(userID);
    }
}
