package com.webapp.buildPC.controller;

import com.webapp.buildPC.domain.Category;
import com.webapp.buildPC.service.interf.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/allCategory")
    public ResponseEntity<List<Category>> getAllCategory(){
        return ResponseEntity.ok().body(categoryService.getAllCategory());
    }

    @GetMapping("/categoryByType")
    public ResponseEntity<List<Category>> getCategoryByType(@RequestParam String categoryTypeID){
        return ResponseEntity.ok().body(categoryService.getCategoryByCategoryTypeID(categoryTypeID));
    }


}
