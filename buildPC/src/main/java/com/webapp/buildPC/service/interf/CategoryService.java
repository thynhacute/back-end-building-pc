package com.webapp.buildPC.service.interf;

import com.webapp.buildPC.domain.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategory();

    List<Category> getCategoryByCategoryTypeID(String cateTypeID);
    Category getCategoryByCategoryID(String categoryID);
}
