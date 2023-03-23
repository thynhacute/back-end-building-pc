package com.webapp.buildPC.domain.Transaction;

import com.webapp.buildPC.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRespone {
    private String defaultAddress;
    private List<Address> addressList;
}
