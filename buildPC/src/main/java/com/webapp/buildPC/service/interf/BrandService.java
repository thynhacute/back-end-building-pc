package com.webapp.buildPC.service.interf;

import com.webapp.buildPC.domain.Brand;

import java.util.List;

public interface BrandService {

    List<Brand> getAllBrands();

    boolean saveBrand(Brand brand);

    List<Brand> fetchAllBrand();

}
