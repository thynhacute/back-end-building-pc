package com.webapp.buildPC.controller;

import com.webapp.buildPC.domain.Brand;
import com.webapp.buildPC.service.interf.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/redisBrand")
    public ResponseEntity<String> saveBrand(@RequestBody Brand brand){
        boolean result = brandService.saveBrand(brand);
        if(result)
            return ResponseEntity.ok("Brand Create Successfully");
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/redisFetchAll")
    public ResponseEntity<List<Brand>> fetchAllBrand(){
        List<Brand> brands;
        brands = brandService.fetchAllBrand();
        return ResponseEntity.ok(brands);
    }


}
