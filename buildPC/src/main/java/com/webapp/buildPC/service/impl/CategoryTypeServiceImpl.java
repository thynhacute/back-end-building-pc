package com.webapp.buildPC.service.impl;

import com.webapp.buildPC.dao.CategoryTypeMapper;
import com.webapp.buildPC.domain.CategoryType;
import com.webapp.buildPC.service.interf.CategoryTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryTypeServiceImpl implements CategoryTypeService {

    private final CategoryTypeMapper categoryTypeMapper;

    @Override
    public List<CategoryType> getAllCategoryType() {
        log.debug("Show the list of CategoryType");
        return categoryTypeMapper.getAllCategoryType();
    }
}
