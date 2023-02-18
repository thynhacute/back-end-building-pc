package com.webapp.buildPC.controller;

import com.webapp.buildPC.domain.Brand;
import com.webapp.buildPC.service.interf.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @GetMapping("/allBrand")
    public ResponseEntity<List<Brand>> getAllBrand(){
        log.debug("Show the list all brands");
        return ResponseEntity.ok().body(brandService.getAllBrands());
    }


}
