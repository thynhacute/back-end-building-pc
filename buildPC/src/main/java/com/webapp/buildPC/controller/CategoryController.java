package com.webapp.buildPC.controller;

import com.webapp.buildPC.domain.Category;
import com.webapp.buildPC.service.interf.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/getAllCategory")
    public ResponseEntity<List<Category>> getAllCategory(){
        return ResponseEntity.ok().body(categoryService.getAllCategory());
    }

    @GetMapping("/getCategoryByType")
    public ResponseEntity<List<Category>> getCategoryByType(@RequestParam String categoryTypeID){
        return ResponseEntity.ok().body(categoryService.getCategoryByCategoryTypeID(categoryTypeID));
    }


}
