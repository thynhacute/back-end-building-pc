package com.webapp.buildPC.service.impl;

import com.webapp.buildPC.dao.AddressMapper;
import com.webapp.buildPC.domain.Address;
import com.webapp.buildPC.service.interf.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressImpl implements AddressService {
    private final AddressMapper addressMapper;
    @Override
    public Address getAddressByAddressID(int addressID) {
        log.debug("Get address by address ID");
        return addressMapper.getAddressByAddressID(addressID);
    }
}
