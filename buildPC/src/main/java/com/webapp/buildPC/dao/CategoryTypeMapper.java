package com.webapp.buildPC.dao;

import com.webapp.buildPC.domain.CategoryType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryTypeMapper {

    List<CategoryType> getAllCategoryType();

}
