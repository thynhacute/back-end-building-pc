package com.webapp.buildPC.service.impl;

import com.webapp.buildPC.dao.PCDetailMapper;
import com.webapp.buildPC.domain.PCDetail;
import com.webapp.buildPC.service.interf.PCDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PCDetailServiceImpl implements PCDetailService {
    private final PCDetailMapper pcDetailMapper;
    @Override
    public List<PCDetail> getPCDetailByProductID(String productID) {
        log.debug("Get list by productID");
        return pcDetailMapper.getPCDetailByProductID(productID);
    }
}
