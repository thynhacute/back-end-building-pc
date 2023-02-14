package com.webapp.buildPC.controller;

import com.webapp.buildPC.domain.CategoryType;
import com.webapp.buildPC.service.interf.CategoryTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categoryType")
public class CategoryTypeController {

    private final CategoryTypeService categoryTypeService;
    @GetMapping("/getAllCategoryType")
    public ResponseEntity<List<CategoryType>> getAllCategoryType(){
        return ResponseEntity.ok().body(categoryTypeService.getAllCategoryType());
    }

}
