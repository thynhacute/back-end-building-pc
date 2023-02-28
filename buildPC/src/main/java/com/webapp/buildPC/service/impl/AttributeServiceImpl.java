package com.webapp.buildPC.service.impl;

import com.webapp.buildPC.dao.AttributeMapper;
import com.webapp.buildPC.domain.Attribute;
import com.webapp.buildPC.service.interf.AttributeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttributeServiceImpl implements AttributeService {

    private final AttributeMapper attributeMapper;
    @Override
    public List<Attribute> getAllAttribute() {
        return attributeMapper.getAllAttribute();
    }
}
