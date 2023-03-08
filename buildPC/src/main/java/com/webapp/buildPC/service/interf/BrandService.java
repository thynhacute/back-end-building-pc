package com.webapp.buildPC.service.interf;

import com.webapp.buildPC.domain.Brand;

import java.util.List;

public interface BrandService {

    List<Brand> getAllBrands();

    Brand findBrandByID(int brandid);

    boolean saveBrand(Brand brand);

    List<Brand> fetchAllBrand();

    Object fetchBrandById(int brandID);

    boolean deleteBrandRedis(int brandID);

}
