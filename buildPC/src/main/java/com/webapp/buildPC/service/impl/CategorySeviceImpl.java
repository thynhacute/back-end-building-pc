package com.webapp.buildPC.service.impl;

import com.webapp.buildPC.dao.CategoryMapper;
import com.webapp.buildPC.domain.Category;
import com.webapp.buildPC.service.interf.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategorySeviceImpl implements CategoryService {


    private final CategoryMapper categoryMapper;
    @Override
    public List<Category> getAllCategory() {
        log.debug("Show the list of Categories");
        return categoryMapper.getAllCategory();
    }

    @Override
    public List<Category> getCategoryByCategoryTypeID(String cateTypeID) {
        log.debug("Get the List Category By Type");
        return categoryMapper.getCategoryByCategoryTypeID(cateTypeID);
    }
}
