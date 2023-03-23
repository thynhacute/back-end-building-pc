package com.webapp.buildPC.dao;

import com.webapp.buildPC.domain.Address;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressMapper {
   Address getAddressByAddressID(int addressID);
}
