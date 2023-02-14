package com.webapp.buildPC.dao;

import com.webapp.buildPC.domain.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<Category> getAllCategory();

    List<Category> getCategoryByCategoryTypeID(String categoryTypeID);



}
